package com.lushihao.qrcode.entity.common;

public class Result {

    /**
     * 是否成功
     */
    private boolean ifSuccess;
    /**
     * 返回内容
     */
    private Object bean;
    /**
     * 展示信息
     */
    private String info;
    /**
     * 错误信息
     */
    private String errorInfo;

    public boolean isIfSuccess() {
        return ifSuccess;
    }

    public void setIfSuccess(boolean ifSuccess) {
        this.ifSuccess = ifSuccess;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Result() {
    }

    public Result(boolean ifSuccess, Object bean, String info, String errorInfo) {
        this.ifSuccess = ifSuccess;
        this.bean = bean;
        this.info = info;
        this.errorInfo = errorInfo;
    }

}
