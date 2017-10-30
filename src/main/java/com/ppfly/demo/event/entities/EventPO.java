package com.ppfly.demo.event.entities;

import com.ppfly.demo.common.entities.WorkflowInfo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 事件实体类
 * Created by ppfly on 2017/9/22.
 */
@Entity
@Table(name = "T_EVENT")
@PrimaryKeyJoinColumn(name = "EVENT_ID")
public class EventPO extends WorkflowInfo {

    private String title;//事件标题
    private String eventCode;//事件编码
    private String eventDesc;//事件描述
    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private Date exceptedSolutionTime;//期望解决时间
    private Integer importanceDegree;//重要度
    private Integer priority;//优先级

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "EVENT_CODE")
    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    @Column(name = "EVENT_DESC")
    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    @Column(name = "EXCEPTED_SOLUTION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getExceptedSolutionTime() {
        return exceptedSolutionTime;
    }

    public void setExceptedSolutionTime(Date exceptedSolutionTime) {
        this.exceptedSolutionTime = exceptedSolutionTime;
    }

    @Column(name = "IMPORTANCEDE_GREE")
    public Integer getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(Integer importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}
