package com.iufc.cloud.events.activiti.task;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description: 指派处理角色
 *
 * @author baoshuang
 * date 2017-10-24  0:29
 */

@AllArgsConstructor
@Data
public class AssigneeRole {
    /**
     * 处理人Id
     */
    private String employeeId;
    /**
     * 处理角色名
     */
    private String roleName;
}
