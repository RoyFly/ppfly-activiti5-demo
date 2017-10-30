package com.ppfly.demo.common.entities;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 工作流基础类
 * Created by ppfly on 2017/9/22.
 */
@Entity
@Table(name = "T_WORKFLOW_MAIN")
@Inheritance(strategy = InheritanceType.JOINED)
public class WorkflowInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    //工单类型
    public static final String WORKORDER_TYPE_LEAVE = "leave";//请假
    public static final String WORKORDER_TYPE_EVENT = "event";//事件
    public static final String WORKORDER_TYPE_DAILYREPORTS = "dailyreports";//日报

    //审核状态
    public static final String PASS_YES = "0";//通过
    public static final String PASS_NO = "1";//不通过

    protected Long id;//业务主键
    private String initiator;//发起人
    private String processInstanceId;//流程实例Id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//工单创建日期
    private String workOrderType;//工单类型
    private String currTaskDefinitionKey;//当前任务定义key(环节Id)
    private String currTaskDefinitionName;//当前任务定义name// (环节名称)
    private String taskId;//（运行）任务Id
    private String currentStatus;//当前（审核）状态【0:通过；1：不通过】

    //-- 临时属性 --//
    // 流程任务
    private Task task;
    private Map<String, Object> variables;
    // 运行中的流程实例
    private ProcessInstance processInstance;
    // 历史的流程实例
    private HistoricProcessInstance historicProcessInstance;
    // 流程定义
    private ProcessDefinition processDefinition;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    @Column(name = "PROCESSINSTANCE_ID")
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Transient
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Transient
    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    @Transient
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    @Transient
    public HistoricProcessInstance getHistoricProcessInstance() {
        return historicProcessInstance;
    }

    public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
        this.historicProcessInstance = historicProcessInstance;
    }

    @Transient
    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }


    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "WORKORDER_TYPE")
    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    @Column(name = "CURR_TASKDEFINITION_KEY")
    public String getCurrTaskDefinitionKey() {
        return currTaskDefinitionKey;
    }

    public void setCurrTaskDefinitionKey(String currTaskDefinitionKey) {
        this.currTaskDefinitionKey = currTaskDefinitionKey;
    }

    @Column(name = "CURR_TASKDEFINITION_NAME")
    public String getCurrTaskDefinitionName() {
        return currTaskDefinitionName;
    }

    public void setCurrTaskDefinitionName(String currTaskDefinitionName) {
        this.currTaskDefinitionName = currTaskDefinitionName;
    }

    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Column(name = "CURRENT_STATUS")
    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
