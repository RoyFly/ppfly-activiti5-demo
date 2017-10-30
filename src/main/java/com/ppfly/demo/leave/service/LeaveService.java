package com.ppfly.demo.leave.service;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.leave.entities.Leave;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;

/**
 * 请假流程Service
 */
public interface LeaveService {
    /**
     * 请假申请
     *
     * @param leave
     * @param processDefinitionKey
     * @return
     * @throws Exception
     */
    ProcessInstance askForLeave(Leave leave, String processDefinitionKey) throws Exception;

    /**
     * 请假审核
     *
     * @param taskId
     * @param businessOper
     */
    void audit(String taskId, BusinessOper businessOper);

}
