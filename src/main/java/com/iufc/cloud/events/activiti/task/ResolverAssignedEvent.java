package com.iufc.cloud.events.activiti.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:  任务处理人被指定事件
 *
 * @author: baoshuang
 * @date: 2017-10-21  21:14
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResolverAssignedEvent {

    /**
     * 流程实例Id
     */
    private String processInstId;
    /**
     * 任务实例ID
     */
    private String taskInstId;

    /**
     * 处理人Id
     */
    private String resolverId;

    /**
     * 被指定日期
     */
    private Date assignedDate;
}
