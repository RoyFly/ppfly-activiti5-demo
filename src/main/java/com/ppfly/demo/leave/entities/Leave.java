package com.ppfly.demo.leave.entities;

import com.ppfly.demo.common.entities.WorkflowInfo;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 请假实体类
 */
@Entity
@Table(name = "T_LEAVE")
@PrimaryKeyJoinColumn(name = "LEAVE_ID")
public class Leave extends WorkflowInfo {

    @NotEmpty(message = "请输入请假标题")
    private String title;//请假标题
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请填写请假开始日期")
//    @Future(message = "请假开始日期必须是一个将来的时间")
    private Date startTime;//申请请假开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "请填写请假结束日期")
//    @Future(message = "请假结束日期必须是一个将来的时间")
    private Date endTime;//申请请假结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date realityStartTime;//实际开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date realityEndTime;//实际结束时间
    private String reason;//请假原因
    private String flag;//是否重新申告标示


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_TIME")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TIME")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    @Column
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REALITY_START_TIME")
    public Date getRealityStartTime() {
        return realityStartTime;
    }

    public void setRealityStartTime(Date realityStartTime) {
        this.realityStartTime = realityStartTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REALITY_END_TIME")
    public Date getRealityEndTime() {
        return realityEndTime;
    }

    public void setRealityEndTime(Date realityEndTime) {
        this.realityEndTime = realityEndTime;
    }

    @Transient
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
