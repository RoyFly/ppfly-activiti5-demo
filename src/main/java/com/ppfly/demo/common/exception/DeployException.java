package com.ppfly.demo.common.exception;

/**
 * 自定义部署异常类
 * Created by ppfly on 2017/9/27.
 */
public class DeployException extends RuntimeException {

    public DeployException(String message) {
        super(message);
    }
}
