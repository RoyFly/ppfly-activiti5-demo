package com.ppfly.demo.common.listener;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 子流程
 * Created by ppfly on 2017/10/9.
 */
@Service
@Transactional
public class SubProcessActivityService {
    private Logger log = LoggerFactory.getLogger(SubProcessActivityService.class);

    /**
     * 设置子流程人员
     *
     * @return 必须返回Collection
     */
    public List resolveUsersForTask() {
        log.info("设置子流程人员");
        List<String> assigneeList = Arrays.asList("cyq", "chifan", "wangzhen");//todo 这是你的业务逻辑
        return assigneeList;
    }

    /**
     * 完成条件（跳出loop）
     *
     * @param execution
     * @return
     */
    public boolean isComplete(ActivityExecution execution) {
        int nrOfInstances = (int) execution.getVariable("nrOfInstances");//实例总数
        int nrOfActiveInstances = (int) execution.getVariable("nrOfActiveInstances");//当前活动的数量，即尚未完成的实例。对于顺序多实例，这将始终为1
        int nrOfCompletedInstances = (int) execution.getVariable("nrOfCompletedInstances");//已完成的实例的数量。
        log.info("总的子流程数量：" + nrOfInstances
                + "---当前获取的子流程数量：" + nrOfActiveInstances
                + "---已经完成的子流程数量：" + nrOfCompletedInstances);
        if (nrOfInstances - nrOfCompletedInstances == 0) {//todo 你的业务,我这里以所有子流程均完成才跳出循环为例 默认的
            return true;
        }
        return false;
    }

}
