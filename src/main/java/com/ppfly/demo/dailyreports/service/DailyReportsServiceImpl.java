package com.ppfly.demo.dailyreports.service;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.common.entities.WorkflowInfo;
import com.ppfly.demo.common.repository.BusinessOperRepository;
import com.ppfly.demo.common.service.BaseWorkflowService;
import com.ppfly.demo.dailyreports.entites.DailyReports;
import com.ppfly.demo.dailyreports.entites.SubDailyReports;
import com.ppfly.demo.dailyreports.repository.DailyReportsRepository;
import com.ppfly.demo.dailyreports.repository.SubDailyReportsRepository;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ppfly on 2017/10/9.
 */
@Service
@Transactional
public class DailyReportsServiceImpl implements DailyReportsService {

    @Autowired
    private BaseWorkflowService baseWorkflowService;
    @Autowired
    private DailyReportsRepository dailyReportsRepository;
    @Autowired
    private SubDailyReportsRepository subDailyReportsRepository;
    @Autowired
    private BusinessOperRepository businessOperRepository;

    public DailyReportsServiceImpl() {
    }

    @Override
    public ProcessInstance collectDaily(DailyReports dailyReports, String processDefinitionKey) throws Exception {
        dailyReports.setWorkOrderType(WorkflowInfo.WORKORDER_TYPE_DAILYREPORTS);
        dailyReports.setCreateTime(new Date());
        dailyReports.setCurrentStatus(WorkflowInfo.PASS_YES);
        dailyReportsRepository.save(dailyReports);
        Map<String, Object> variables = new HashMap<>();
        ProcessInstance processInstance = baseWorkflowService.startWorkflow(dailyReports, processDefinitionKey, variables);
        return processInstance;
    }

    @Override
    public void fillInTheDaily(SubDailyReports subDailyReports, String taskId) {
        subDailyReportsRepository.save(subDailyReports);
        String teamLeader = subDailyReports.getTeamLeader();
        Map<String, Object> variables = new HashMap<>();
        variables.put("teamLeader", teamLeader);
        BusinessOper businessOper = new BusinessOper();
        businessOper.setBusinessId(subDailyReports.getBusinessId());
        businessOper.setSubBusinessId(subDailyReports.getId());
        businessOper.setOperTime(new Date());
        businessOper.setComments(subDailyReports.getUserName() + "提交了日报");
        businessOperRepository.save(businessOper);
        baseWorkflowService.complete(taskId, variables);
    }

    @Override
    public void audit(String taskId, BusinessOper businessOper) {
        businessOperRepository.save(businessOper);
        String flag = businessOper.getFlag();
        Map map = new HashMap();
        map.put("flag", flag);
        baseWorkflowService.complete(taskId, map);
    }
}
