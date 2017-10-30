package com.iufc.cloud.events.activiti.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 流程已结束事件
 *
 * @author: baoshuang
 * @date: 2017-10-21  21:21
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessInstCompletedEvent {
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
     * 流程结束日期
     */
    private Date completedDate;
}
