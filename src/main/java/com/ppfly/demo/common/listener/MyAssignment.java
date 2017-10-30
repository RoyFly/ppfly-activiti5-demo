package com.ppfly.demo.common.listener;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 指派人:
 * ①指定人
 * ②指派候选人
 * Created by ppfly on 2017/10/16.
 */
@Service
@Transactional
public class MyAssignment {
    private Logger log = LoggerFactory.getLogger(MyAssignment.class);

    /**
     * 指定人
     *
     * @param execution
     * @return String
     */
    public String assignee(ActivityExecution execution) {
        String assignee = "wangwu";
        log.info("在Assignees中指定MyAssignment#assignee：" + assignee);
        return assignee;
    }

    /**
     * 指派候选人
     *
     * @param execution
     * @return Collection
     */
    public List<String> getCandidateUssers(ActivityExecution execution) {
        List<String> candidateUsers = Arrays.asList("cyq", "chifan", "wangzhen");
        log.info("在Assignees中指定MyAssignment#getCandidateUssers：" + candidateUsers);
        return candidateUsers;
    }
}
