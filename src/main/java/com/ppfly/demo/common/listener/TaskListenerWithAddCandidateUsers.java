package com.ppfly.demo.common.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 带指定候选人的任务监听器
 * Created by ppfly on 2017/9/21.
 */
@Service("taskListenerWithAddCandidateUsers")
@Transactional
public class TaskListenerWithAddCandidateUsers implements TaskListener {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private FixedValue roleCodeName; // 同流程图中fieldName
    private JuelExpression expression;

    /**
     * 用来指定任务的办理人
     *
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        //指定个人任务的候选人
        //用法：通过类去查询数据库，将下一个任务的候选人查询获取，然后通过addCandidateUsers()的方法指定任务的候选人
        String userName = (String) delegateTask.getVariable("applicant");
        String eventName = delegateTask.getEventName();
        if ("create".endsWith(eventName)) {
            log.info("=======TaskListenerWithAddCandidateUsers #create（创建）申告人" + userName + "==================");
            List<String> candidateUsers = new ArrayList<>();
            candidateUsers.add("lisi");
            candidateUsers.add("lisisi");
            delegateTask.addCandidateUsers(candidateUsers);
            log.info("==================TaskListenerWithAddCandidateUsers #create2（创建）==================");
        } else if ("assignment".endsWith(eventName)) {
            log.info("========TaskListenerWithAddCandidateUsers #assignment（分配）==================");
        } else if ("complete".endsWith(eventName)) {
            log.info("=======TaskListenerWithAddCandidateUsers #complete（完成）==================");
        } else if ("delete".endsWith(eventName)) {
            log.info("======TaskListenerWithAddCandidateUsers #delete（删除）==================");
        }
    }

    public FixedValue getRoleCodeName() {
        return roleCodeName;
    }

    public void setRoleCodeName(FixedValue roleCodeName) {
        this.roleCodeName = roleCodeName;
    }


    public JuelExpression getExpression() {
        return expression;
    }

    public void setExpression(JuelExpression expression) {
        this.expression = expression;
    }
}
