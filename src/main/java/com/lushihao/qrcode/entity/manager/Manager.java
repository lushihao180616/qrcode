package com.lushihao.qrcode.entity.manager;

public class Manager {

    /**
     * 管理员标识
     */
    private String code;
    /**
     * 管理员名称
     */
    private String name;
    /**
     * 管理员手机号
     */
    private String phone;
    /**
     * 管理员手机号
     */
    private String address;
    /**
     * 管理员身份证号
     */
    private String idCard;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Manager() {
    }

    public Manager(String code, String name, String phone, String address, String idCard) {
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.idCard = idCard;
    }

}
