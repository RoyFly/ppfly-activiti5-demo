package com.iufc.cloud.events.activiti.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 任务被完成事件
 *
 * @author: baoshuang
 * @date: 2017-10-21  21:16
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskInstCompletedEvent {
    /**
     * 流程实例Id
     */
    private String processInstId;
    /**
     * 任务实例Id
     */
    private String taskInstId;

    /**
     * 任务完成日期
     */
    private Date completedDate;
}
