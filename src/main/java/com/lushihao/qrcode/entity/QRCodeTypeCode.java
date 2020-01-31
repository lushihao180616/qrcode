package com.lushihao.qrcode.entity;

/**
 * 生成的二维码型号及相关信息
 */
public class QRCodeTypeCode {

    /**
     * 标识
     */
    private String code;
    /**
     * 花费
     */
    private double money;
    /**
     * 是否只有二维码
     */
    private boolean ifOnly;
    /**
     * 创建二维码方式（0:热门，1:最初）
     */
    private String arti;
    /**
     * 背景是否透明（Y:透明，N:不透明）
     */
    private String transparent;
    /**
     * 起绘点X
     */
    private int x;
    /**
     * 起绘点Y
     */
    private int y;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isIfOnly() {
        return ifOnly;
    }

    public void setIfOnly(boolean ifOnly) {
        this.ifOnly = ifOnly;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    public String getTransparent() {
        return transparent;
    }

    public void setTransparent(String transparent) {
        this.transparent = transparent;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public QRCodeTypeCode(String code, double money, boolean ifOnly, String arti, String transparent, int x, int y) {
        this.code = code;
        this.money = money;
        this.ifOnly = ifOnly;
        this.arti = arti;
        this.transparent = transparent;
        this.x = x;
        this.y = y;
    }

}
