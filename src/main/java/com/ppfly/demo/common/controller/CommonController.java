package com.ppfly.demo.common.controller;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.common.entities.PlainModel;
import com.ppfly.demo.common.entities.WorkflowInfo;
import com.ppfly.demo.common.service.BaseWorkflowService;
import com.ppfly.demo.dailyreports.entites.SubDailyReports;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通用流程Handler
 * Created by ppfly on 2017/9/19.
 */
@Controller
public class CommonController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected BaseWorkflowService baseWorkflowService;

    /**
     * 跳转到在线流程设计器页面
     *
     * @return
     */
    @GetMapping("/goToDiagramPage")
    public String modelList() {
        return "modeler";
    }

    /**
     * 跳转到新建一个空模型页面
     *
     * @return
     */
    @GetMapping("/goToCreateNewModel")
    public String goToCreateNewModel(@ModelAttribute("plainModel") PlainModel plainModel) {
        return "createNewModel";
    }

    /**
     * 我的待办(任务)
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/todoTaskList/{userName}", method = RequestMethod.GET)
    public String getToDoTaskList(Map<String, Object> map, @PathVariable("userName") String userName) {
        List<WorkflowInfo> workflowInfoList = baseWorkflowService.findTodoTasks(userName);
        map.put("workflowInfoList", workflowInfoList);
        map.put("userName", userName);
        return "todoTask";
    }


    /**
     * 签收任务
     */
    @GetMapping(value = "/com/iufc/cloud/events/activiti/task/claim/{taskId}")
    public String claim(@PathVariable("taskId") String taskId, @RequestParam("userName") String userName) {
        baseWorkflowService.claim(taskId, userName);
        return "success";
    }

    /**
     * 弹出处理框
     */
    @GetMapping(value = "/com/iufc/cloud/events/activiti/task/goToDeal/{taskId}")
    public String goToAudit(Map<String, Object> map, @PathVariable("taskId") String taskId,
                            @RequestParam("businessId") String businessId, @RequestParam("userName") String userName) {
        WorkflowInfo workflowInfo = baseWorkflowService.getWorkflowInfoById(businessId);
        List<BusinessOper> businessOperList = baseWorkflowService.getBusinessOperListByBizId(businessId);
        map.put("taskId", taskId);
        map.put("businessId", businessId);
        map.put("userName", userName);
        map.put("workflowInfo", workflowInfo);
        map.put("businessOperList", businessOperList);
        String workOrderType = workflowInfo.getWorkOrderType();
        String currTaskDefinitionKey = workflowInfo.getCurrTaskDefinitionKey();
        switch (workOrderType) {
            case WorkflowInfo.WORKORDER_TYPE_LEAVE: {
                log.info("请假");
                return "leave/auditLeave";
            }
            case WorkflowInfo.WORKORDER_TYPE_DAILYREPORTS: {
                log.info("日报");
                map.put("subDailyReports", new SubDailyReports());
                if ("FILL_IN_DAILY".equals(currTaskDefinitionKey)) {
                    return "dailyreports/fillInTheDaily";
                } else if ("AUDIT_DAILY".equals(currTaskDefinitionKey) || "GATHER_DAILY".equals(currTaskDefinitionKey)) {
                    return "dailyreports/auditDaily";
                }else if("AMEND_DAILY".equals(currTaskDefinitionKey)){
                    return "dailyreports/fillInTheDaily";
                }
            }
        }
        return "fail";
    }


    /**
     * 获取（所有）流程定义列表
     *
     * @return
     */
    @GetMapping(value = "/getProcessDefinitionList")
    public String getProcessDefinitionList(Map<String, Object> map) {
        List<ProcessDefinition> processDefinitionList = baseWorkflowService.getProcessDefinitionList();
        map.put("processDefinitionList", processDefinitionList);
        return "processDefinitionList";
    }


    /**
     * 获取运行中流程实例
     *
     * @return
     */
    @GetMapping(value = "/getRunningProcessInstanceList")
    public String getRunningProcessInstanceList(Map<String, Object> map) {
        List<ProcessInstance> processInstanceList = baseWorkflowService.getRunningProcessInstanceList();
        map.put("processInstanceList", processInstanceList);
        return "processInstanceList";
    }


    /**
     * 获取已结束的流程实例
     *
     * @return
     */
    @GetMapping(value = "/getFinishedProcessInstanceList")
    public String getFinishedProcessInstanceList(Map<String, Object> map) {
        List<HistoricProcessInstance> historicProcessInstanceList = baseWorkflowService.getFinishedProcessInstanceList();
        map.put("historicProcessInstanceList", historicProcessInstanceList);
        return "historicProcessInstanceList";
    }

    /**
     * 取回流程(任务撤回)
     *
     * @return
     */
    @GetMapping(value = "/callBack/{taskId}")
    public String callBackTask(@PathVariable("taskId") String taskId) {
        try {
            baseWorkflowService.callBackTask(taskId);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }


}
