package com.ppfly.demo.common.entities;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 值对象
 * 空模型
 * Created by ppfly on 2017/9/28.
 */
public class PlainModel {
    private String name;//名称
    private String description;//描述

    @NotEmpty(message = "名称不能为空")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
