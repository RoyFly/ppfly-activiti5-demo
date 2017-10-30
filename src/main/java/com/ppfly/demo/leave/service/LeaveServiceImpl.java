package com.ppfly.demo.leave.service;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.common.entities.WorkflowInfo;
import com.ppfly.demo.common.repository.BusinessOperRepository;
import com.ppfly.demo.common.service.BaseWorkflowService;
import com.ppfly.demo.leave.entities.Leave;
import com.ppfly.demo.leave.repository.LeaveRepository;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 请假流程Service实现类
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private BaseWorkflowService baseWorkflowService;
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private BusinessOperRepository businessOperRepository;

    @Override
    @Transactional
    public ProcessInstance askForLeave(Leave leave, String processDefinitionKey) throws Exception {
        leave.setCreateTime(new Date());
        leave.setWorkOrderType(WorkflowInfo.WORKORDER_TYPE_LEAVE);
        leave.setCurrentStatus(WorkflowInfo.PASS_YES);
        leaveRepository.save(leave);
        Map<String, Object> variables = new HashMap<>();
        String flag = leave.getFlag();
        if (flag != null && !"".equals(flag)) {//调整请假申请
            variables.put("reApply", flag);
            baseWorkflowService.complete(leave.getTaskId(), variables);
            return null;
        }
        ProcessInstance processInstance = baseWorkflowService.startWorkflow(leave, processDefinitionKey, variables);
        return processInstance;
    }

    @Override
    public void audit(String taskId, BusinessOper businessOper) {
        businessOperRepository.save(businessOper);
        String flag = businessOper.getFlag();
        Map map = new HashMap();
        map.put("flag", flag);
//        if ("zhaoliu".equals(businessOper.getComments())) {
//            map.put("renshi", businessOper.getComments());
//        }
        Leave leave = leaveRepository.findOne(businessOper.getBusinessId());
        leave.setCurrentStatus(flag);
        baseWorkflowService.complete(taskId, map);
    }

}
