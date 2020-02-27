package com.lushihao.qrcode.entity.user;

import com.lushihao.qrcode.entity.business.Business;

public class UserInfo {

    /**
     * 用户标识
     */
    private String code;
    /**
     * 用户类型
     */
    private UserType userType;
    /**
     * 剩余可创建二维码数量
     */
    private int count;
    /**
     * mac地址
     */
    private String macAddress;
    /**
     * 商家信息
     */
    private Business business;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public UserInfo() {
    }

    public UserInfo(String code, UserType userType, int count, String macAddress, Business business) {
        this.code = code;
        this.userType = userType;
        this.count = count;
        this.macAddress = macAddress;
        this.business = business;
    }

}
