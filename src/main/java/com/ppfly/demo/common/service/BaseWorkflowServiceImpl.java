package com.ppfly.demo.common.service;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.common.entities.WorkflowInfo;
import com.ppfly.demo.common.exception.CallBackException;
import com.ppfly.demo.common.repository.BaseWorkflowRepository;
import com.ppfly.demo.common.repository.BusinessOperRepository;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工作流基础ServiceImpl
 * Created by ppfly on 2017/9/22.
 */
@Service
public class BaseWorkflowServiceImpl implements BaseWorkflowService {

    private Logger log = LoggerFactory.getLogger(BaseWorkflowServiceImpl.class);

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private BaseWorkflowRepository baseWorkflowRepository;

    @Autowired
    private BusinessOperRepository businessOperRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProcessInstance startWorkflow(WorkflowInfo workflowInfo, String processDefinitionKey, Map<String, Object> variables) throws Exception {
        IdentityService identityService = processEngine.getIdentityService();
        String authenticatedUserId = workflowInfo.getInitiator();
        identityService.setAuthenticatedUserId(authenticatedUserId);  // 用来设置启动流程的人员ID，引擎会自动把用户Id保存到activiti:initiator中

        String businessKey = workflowInfo.getId().toString();//业务Id
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        String processInstanceId = processInstance.getId();
        workflowInfo.setProcessInstanceId(processInstanceId);
        return processInstance;
    }

    @Override
    @Transactional
//    @Transactional(readOnly = true) //todo 此处去除只读事务，来更新workflowInfo
    public List<WorkflowInfo> findTodoTasks(String userName) {
        List<WorkflowInfo> results = new ArrayList<WorkflowInfo>();
        TaskService taskService = processEngine.getTaskService();
        // 根据当前人的ID查询
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userName);//已签出或分配给用户的任务，或等待用户(候选用户或组)签出的任务的查询
        List<Task> tasks = taskQuery.list();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null) {
                continue;
            }
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }
            WorkflowInfo workflowInfo = baseWorkflowRepository.findOne(new Long(businessKey));
            workflowInfo.setTaskId(task.getId());
            workflowInfo.setCurrTaskDefinitionKey(task.getTaskDefinitionKey());
            workflowInfo.setCurrTaskDefinitionName(task.getName());
            workflowInfo.setTask(task);
            workflowInfo.setProcessInstance(processInstance);
            workflowInfo.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            results.add(workflowInfo);
        }
        return results;
    }

    @Override
    public WorkflowInfo getWorkflowInfoById(String businessId) {
        WorkflowInfo workflowInfo = baseWorkflowRepository.findOne(new Long(businessId));
        return workflowInfo;
    }

    @Override
    public List<BusinessOper> getBusinessOperListByBizId(String businessId) {
        List<BusinessOper> businessOperList = businessOperRepository.getAllByBusinessId(new Long(businessId));
        return businessOperList;
    }


    /**
     * 查询流程定义对象
     *
     * @param processDefinitionId 流程定义ID
     * @return
     */
    protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }


    @Override
    public void claim(String taskId, String userName) {
        TaskService taskService = processEngine.getTaskService();
        taskService.claim(taskId, userName);
    }

    @Override
    public void complete(String taskId, Map map) {
        TaskService taskService = processEngine.getTaskService();
        taskService.complete(taskId, map);
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitionList() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().active().orderByDeploymentId().desc();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        return processDefinitionList;
    }

    @Override
    public List<ProcessInstance> getRunningProcessInstanceList() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery().active().orderByProcessInstanceId().desc();
        List<ProcessInstance> processInstanceList = processInstanceQuery.list();
        return processInstanceList;
    }

    @Override
    public List<HistoricProcessInstance> getFinishedProcessInstanceList() {
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().finished().orderByProcessInstanceEndTime().desc();
        List<HistoricProcessInstance> historicProcessInstanceList = historicProcessInstanceQuery.list();
        return historicProcessInstanceList;
    }

    @Override
    public void callBackTask(String taskId) throws Exception {
        HistoryService historyService = processEngine.getHistoryService();
        // 取得当前任务
        HistoricTaskInstance currTask = historyService
                .createHistoricTaskInstanceQuery().taskId(taskId)
                .singleResult();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(currTask
                        .getProcessDefinitionId());
        if (definition == null) {
            log.error("流程定义未找到");
            throw new CallBackException("流程定义未找到");
        }
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 取得流程实例
        ProcessInstance instance = runtimeService
                .createProcessInstanceQuery()
                .processInstanceId(currTask.getProcessInstanceId())
                .singleResult();
        if (instance == null) {
            log.error("流程已经结束");
            throw new CallBackException("流程已经结束");
        }
        Map<String, Object> variables = instance.getProcessVariables();
        // 取得下一步活动
        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                .findActivity(currTask.getTaskDefinitionKey());
        List<PvmTransition> nextTransitionList = currActivity
                .getOutgoingTransitions();
        for (PvmTransition nextTransition : nextTransitionList) {
            PvmActivity nextActivity = nextTransition.getDestination();
            List<HistoricTaskInstance> completeTasks = historyService
                    .createHistoricTaskInstanceQuery()
                    .processInstanceId(instance.getId())
                    .taskDefinitionKey(nextActivity.getId()).finished()
                    .list();
            int finished = completeTasks.size();
            if (finished > 0) {
                log.error("存在已经完成的下一步，流程不能取回");
                throw new CallBackException("存在已经完成的下一步，流程不能取回");
            }
            TaskService taskService = processEngine.getTaskService();
            List<Task> nextTasks = taskService.createTaskQuery().processInstanceId(instance.getId())
                    .taskDefinitionKey(nextActivity.getId()).list();
            for (Task nextTask : nextTasks) {
                //取活动，清除活动方向
                List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
                List<PvmTransition> pvmTransitionList = nextActivity
                        .getOutgoingTransitions();
                for (PvmTransition pvmTransition : pvmTransitionList) {
                    oriPvmTransitionList.add(pvmTransition);
                }
                pvmTransitionList.clear();
                //建立新方向
                ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
                        .findActivity(nextTask.getTaskDefinitionKey());
                TransitionImpl newTransition = nextActivityImpl
                        .createOutgoingTransition();
                newTransition.setDestination(currActivity);
                //完成任务
                taskService.complete(nextTask.getId(), variables);
                historyService.deleteHistoricTaskInstance(nextTask.getId());
                //恢复方向
                currActivity.getIncomingTransitions().remove(newTransition);
                List<PvmTransition> pvmTList = nextActivity
                        .getOutgoingTransitions();
                pvmTList.clear();
                for (PvmTransition pvmTransition : oriPvmTransitionList) {
                    pvmTransitionList.add(pvmTransition);
                }
            }
            historyService.deleteHistoricTaskInstance(currTask.getId());
        }
    }
}
