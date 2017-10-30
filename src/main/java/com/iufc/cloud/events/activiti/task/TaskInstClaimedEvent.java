package com.iufc.cloud.events.activiti.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 任务被签出事件
 *
 * @author: baoshuang
 * @date: 2017-10-21  21:23
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskInstClaimedEvent {
    /**
     * 流程实例Id
     */
    private String processInstId;

    /**
     * 任务实例Id
     */
    private String taskInstId;

    /**
     * 任务被签出Id
     */
    private String claimedUserId;

    /**
     * 签出日期
     */
    private Date claimedDate;
}
