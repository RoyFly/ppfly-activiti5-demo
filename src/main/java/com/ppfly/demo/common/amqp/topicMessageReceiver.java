package com.ppfly.demo.common.amqp;

import com.iufc.cloud.events.activiti.task.ActivitiTaskInstCompletedEvent;
import com.iufc.cloud.events.activiti.task.ActivitiTaskInstCreatedEvent;
import iufc.cn.activiti.amqp.ExecutionVO;
import iufc.cn.activiti.amqp.TaskVO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cyq on 2017/10/18.
 */
@Component
@RabbitListener(queues = AmqpConfig.QUENE_NAME, containerFactory = "rabbitListenerContainerFactory")
public class topicMessageReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("process(String msg)接受到消息：" + msg);
    }

    @RabbitHandler
    public void process(Map map) {
        System.out.println("process(Map map)接受到消息：" + map.get("type"));
    }

//    @RabbitHandler
//    public void process(@Payload TaskVO taskVO) {
//        System.out.println("process(@Payload TaskVO taskVO)接受到消息：" + taskVO.getName());
//    }
//
//    @RabbitHandler
//    public void process(@Payload ActivitiTaskInstCreatedEvent activitiTaskInstCreatedEvent) {
//        System.out.println("process(@Payload ExecutionVO executionVO)接受到消息：" + activitiTaskInstCreatedEvent.getBusinessKey());
//    }
//
//    @RabbitHandler
//    public void process(@Payload ExecutionVO executionVO) {
//        System.out.println("process(@Payload ExecutionVO executionVO)接受到消息：" + executionVO.getProcessDefinitionId());
//    }
//
////    @RabbitHandler
////    public void process(@Payload ActivitiTaskInstCompletedEvent activitiTaskInstCompletedEvent) {
////        System.out.println("process(@Payload ExecutionVO executionVO)接受到消息：" + activitiTaskInstCompletedEvent.getBusinessKey());
////    }
    @RabbitHandler
    public void process(Object o) {
        System.out.println(o.getClass().getName());
        System.out.println("process(Map map)接受到消息：" + o);
    }

}