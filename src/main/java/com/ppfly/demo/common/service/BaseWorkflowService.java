package com.ppfly.demo.common.service;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.common.entities.WorkflowInfo;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;

/**
 * 工作流基础Service
 * Created by ppfly on 2017/9/22.
 */
public interface BaseWorkflowService {

    /**
     * 开始流程引擎
     *
     * @param workflowInfo
     * @param processDefinitionKey
     * @param variables            : the variables to pass, can be null.
     * @return
     * @throws Exception
     */
    ProcessInstance startWorkflow(WorkflowInfo workflowInfo, String processDefinitionKey, Map<String, Object> variables) throws Exception;

    /**
     * 查看我的待办任务
     *
     * @param userName
     */
    List<WorkflowInfo> findTodoTasks(String userName);


    /**
     * 通过业务Id获取业务相关信息
     *
     * @param businessId
     * @return
     */
    WorkflowInfo getWorkflowInfoById(String businessId);


    /**
     * 通过业务Id获取操作列表
     *
     * @param businessId
     * @return
     */
    List<BusinessOper> getBusinessOperListByBizId(String businessId);


    /**
     * 签出任务
     *
     * @param taskId
     * @param userName
     */
    void claim(String taskId, String userName);

    /**
     * 处理（审核）/完成任务
     *
     * @param taskId
     * @param map
     */
    void complete(String taskId, Map map);

    /**
     * 获取（所有）流程定义列表
     *
     * @return
     */
    List<ProcessDefinition> getProcessDefinitionList();

    /**
     * 获取运行中流程实例
     *
     * @return
     */
    List<ProcessInstance> getRunningProcessInstanceList();

    /**
     * 获取已结束的流程实例
     *
     * @return
     */
    List<HistoricProcessInstance> getFinishedProcessInstanceList();

    /**
     * 取回流程(任务撤回)
     *
     * @param taskId
     */
    void callBackTask(String taskId) throws Exception;

}
