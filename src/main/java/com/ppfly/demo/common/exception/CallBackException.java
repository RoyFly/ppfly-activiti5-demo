package com.ppfly.demo.common.exception;

/**
 * 自定义取回流程(任务撤回)异常类
 * Created by ppfly on 2017/9/27.
 */
public class CallBackException extends RuntimeException {

    public CallBackException(String message) {
        super(message);
    }
}
