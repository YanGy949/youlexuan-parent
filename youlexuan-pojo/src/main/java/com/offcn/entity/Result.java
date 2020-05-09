package com.offcn.entity;

import java.io.Serializable;

/**
 * 返回结果封装
 */
public class Result implements Serializable {
    private boolean success;//是否成功
    private String message;//执行结果信息

    public Result() {
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
