package com.ppfly.demo.dailyreports.service;

import com.ppfly.demo.common.entities.BusinessOper;
import com.ppfly.demo.dailyreports.entites.DailyReports;
import com.ppfly.demo.dailyreports.entites.SubDailyReports;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 日报service
 * Created by ppfly on 2017/10/9.
 */
public interface DailyReportsService {

    /**
     * 发起收集日报
     *
     * @param dailyReports
     * @param processDefinitionKey
     * @return
     */
    ProcessInstance collectDaily(DailyReports dailyReports, String processDefinitionKey) throws Exception;

    /**
     * 填写日报
     *
     * @param subDailyReports
     * @param taskId
     */
    void fillInTheDaily(SubDailyReports subDailyReports, String taskId);

    /**
     * 处理（审核）/完成日报
     *
     * @param taskId
     * @param businessOper
     */
    void audit(String taskId, BusinessOper businessOper);


}

