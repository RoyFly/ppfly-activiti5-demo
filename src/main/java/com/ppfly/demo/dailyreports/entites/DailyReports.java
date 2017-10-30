package com.ppfly.demo.dailyreports.entites;

import com.ppfly.demo.common.entities.WorkflowInfo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


/**
 * 日报实体类
 */
@Entity
@Table(name = "T_DAILYREPORTS")
@PrimaryKeyJoinColumn(name = "DAILYREPORTS_ID")
public class DailyReports extends WorkflowInfo {

    @NotEmpty(message = "请输入准备收集日报的标题")
    private String title;//标题

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
