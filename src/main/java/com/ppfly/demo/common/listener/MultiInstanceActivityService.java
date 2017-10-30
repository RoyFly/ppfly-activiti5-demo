package com.ppfly.demo.common.listener;

import com.ppfly.demo.common.listener.GlobalExecutionListenerImpl;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 会签(多实例任务)
 * 支持串审、并审
 * Created by ppfly on 2017/9/28.
 */
@Service
@Transactional
public class MultiInstanceActivityService {
    private Logger log = LoggerFactory.getLogger(GlobalExecutionListenerImpl.class);

    /**
     * 设置会签环节的人员
     *
     * @return 必须返回Collection
     */
    public List resolveUsersForTask() {
        log.info("设置会签环节的人员");
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
        log.info("总的会签任务数量：" + nrOfInstances
                + "---当前获取的会签任务数量：" + nrOfActiveInstances
                + "---已经完成的会签任务数量：" + nrOfCompletedInstances);
        if (nrOfInstances - nrOfCompletedInstances <= 1) {//todo 你的业务,我这里以“当前活动的数量等于1个”就跳出loop为例
            return true;
        }
        return false;
    }

}
