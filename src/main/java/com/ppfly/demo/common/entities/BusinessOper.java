package com.ppfly.demo.common.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 业务操作记录类
 */
@Entity
@Table(name = "T_BUSINESS_OPER")
public class BusinessOper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;//主键
    private Long businessId;//业务表Id
    private Long subBusinessId;//业务表子流程Id
    private String flag;//通过与否标示
    private String comments;//审批意见
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;//操作时间

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

    @Column(name = "SUB_BUSINESS_ID")
    public Long getSubBusinessId() {
        return subBusinessId;
    }

    public void setSubBusinessId(Long subBusinessId) {
        this.subBusinessId = subBusinessId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Column(name = "OPER_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
}
