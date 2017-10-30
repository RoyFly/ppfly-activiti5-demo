package com.ppfly.demo.common.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务监听器3
 * Created by ppfly on 2017/9/21.
 */
@Component
@Transactional
public class TaskListenerWithNothing implements TaskListener {
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
        String userName = (String) delegateTask.getVariable("applicant");
        log.info(">>>>>>>>>>>>>>>>>>TaskListenerWithNothing 监听到申告人：" + userName);
        String eventName = delegateTask.getEventName();
        if ("create".endsWith(eventName)) {
            log.info("==================TaskListenerWithNothing #create（创建）==================");
        } else if ("assignment".endsWith(eventName)) {
            log.info("==================TaskListenerWithNothing #assignment（分配）==================");
        } else if ("complete".endsWith(eventName)) {
            log.info("==================TaskListenerWithNothing #complete（完成）==================");
        } else if ("delete".endsWith(eventName)) {
            log.info("==================TaskListenerWithNothing #delete（删除）==================");
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
