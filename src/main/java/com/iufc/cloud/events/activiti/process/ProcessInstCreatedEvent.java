package com.iufc.cloud.events.activiti.process;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:流程已创建事件
 *
 * @author: baoshuang
 * @date: 2017-10-21  21:21
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessInstCreatedEvent {
    /**
     * 流程实例Id
     */
    private String processInstanceId;

    /**
     * 流程定义Id
     */
    private String processDefId;

    /**
     * 业务Id
     */
    private String businessKey;

    /**
     * 流程创建日期
     */
    private Date createdDate;
}
