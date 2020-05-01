package com.lushihao.qrcode.entity.bucket;

public class Bucket {

    /**
     * 云存储名称
     */
    private String name;
    /**
     * 云存储IP
     */
    private String ip;
    /**
     * 云存储端口
     */
    private String port;
    /**
     * 云存储用户名
     */
    private String userName;
    /**
     * 云存储密码
     */
    private String pwd;
    /**
     * 云存储是否启用
     */
    private boolean ifUse;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isIfUse() {
        return ifUse;
    }

    public void setIfUse(boolean ifUse) {
        this.ifUse = ifUse;
    }

}
