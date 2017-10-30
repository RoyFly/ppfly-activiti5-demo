package com.iufc.cloud.events.activiti.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 任务被指定候选人事件
 *
 * @author: baoshuang
 * @date: 2017-10-21  21:05
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateUserAssignedEvent {

    /**
     * 流程实例Id
     */
    private String processInstId;
    /**
     * 任务实例Id
     */
    private String taskInstId;
    /**
     * 任务候选人Id
     */
    private String candidateUserId;

    /**
     * 被指定日期
     */
    private Date assignedDate;
}
