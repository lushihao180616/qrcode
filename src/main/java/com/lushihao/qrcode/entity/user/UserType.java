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

    public UserType() {
    }

    public UserType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
