package com.iufc.cloud.events.activiti.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 任务被创建事件
 *
 * @author: baoshuang
 * @date: 2017-10-21  21:15
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivitiTaskInstCreatedEvent {
    /**
     * 流程实例Id
     */
    private String processInstId;

    /**
     * 业务Id
     */
    private String businessKey;
    /**
     * 任务实例Id
     */
    private String taskInstId;

    /**
     * 任务环节的定义ID
     */
    private String taskDefId;

    /**
     * 处理候选人
     */
    private Set<AssigneeRole> candidates;

    /**
     * 任务被创建日期
     */
    private Date createDate;
}
