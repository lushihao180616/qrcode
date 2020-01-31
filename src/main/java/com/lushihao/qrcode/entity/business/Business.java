package com.lushihao.qrcode.entity.business;

public class Business {

    /**
     * 商家标识
     */
    private String code;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 联系人
     */
    private String businessName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

}
