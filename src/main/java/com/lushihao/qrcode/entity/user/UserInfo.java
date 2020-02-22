package com.lushihao.qrcode.entity.user;

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

    public UserInfo() {
    }

    public UserInfo(String code, UserType userType, int count, String macAddress) {
        this.code = code;
        this.userType = userType;
        this.count = count;
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "code='" + code + '\'' +
                ", userType=" + userType +
                ", count=" + count +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }

}
