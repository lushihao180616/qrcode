package com.lushihao.qrcode.entity.user;

import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.manager.Manager;

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
     * 备用mac地址
     */
    private String macAddress2;
    /**
     * 商家信息
     */
    private Business business;
    /**
     * 管理员信息
     */
    private Manager manager;

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

    public String getMacAddress2() {
        return macAddress2;
    }

    public void setMacAddress2(String macAddress2) {
        this.macAddress2 = macAddress2;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public UserInfo() {
    }

    public UserInfo(String code, UserType userType, int count, String macAddress, String macAddress2, Business business, Manager manager) {
        this.code = code;
        this.userType = userType;
        this.count = count;
        this.macAddress = macAddress;
        this.macAddress2 = macAddress2;
        this.business = business;
        this.manager = manager;
    }

}
