package com.ppfly.demo.common.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 全局的监听器
 * Created by ppfly on 2017/9/26.
 */
@Service("globalExecutionListener")
@Transactional
public class GlobalExecutionListenerImpl implements ExecutionListener {
    private Logger log = LoggerFactory.getLogger(GlobalExecutionListenerImpl.class);

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        if ("start".equals(eventName)) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GlobalExecutionListenerImpl#start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } else if ("end".equals(eventName)) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GlobalExecutionListenerImpl#end>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } else if ("take".equals(eventName)) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>GlobalExecutionListenerImpl#take>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }
}
