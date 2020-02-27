package com.lushihao.qrcode.entity.user;

public class UserType {

    /**
     * 类型标识
     */
    private String code;
    /**
     * 类型名称
     */
    private String name;
    /**
     * 类型类型
     */
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserType() {
    }

    public UserType(String code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

}
