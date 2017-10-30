package com.ppfly.demo.dailyreports.entites;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 日报子流程实体类
 */
@Entity
@Table(name = "T_DAILYREPORTS_SUB")
public class SubDailyReports implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;//主键
    private Long businessId;//业务表Id
    private String userName;//日报填写人
    @NotEmpty(message = "请输入日报内容")
    private String content;//请假原因
    @NotEmpty(message = "请输入你的组长")
    private String teamLeader;
    private String taskId;//（运行）任务Id

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BUSINESS_ID")
    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
