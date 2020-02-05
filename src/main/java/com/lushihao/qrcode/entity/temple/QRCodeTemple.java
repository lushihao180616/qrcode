package com.lushihao.qrcode.entity.temple;

/**
 * 生成的二维码型号及相关信息
 */
public class QRCodeTemple {

    /**
     * 标识
     */
    private String code;
    /**
     * 花费
     */
    private double money;
    /**
     * 是否只有二维码(true:只有二维码)
     */
    private boolean ifOnly;
    /**
     * 是否显示商标(true:显示)
     */
    private boolean ifShowLogo;
    /**
     * 是否自定义背景(true:显示)
     */
    private boolean ifSelfBg;
    /**
     * 创建二维码方式（0:热门，1:最初）
     */
    private String arti;
    /**
     * 起绘点X
     */
    private int width;
    /**
     * 起绘点Y
     */
    private int height;
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

    public boolean isIfShowLogo() {
        return ifShowLogo;
    }

    public void setIfShowLogo(boolean ifShowLogo) {
        this.ifShowLogo = ifShowLogo;
    }

    public boolean isIfSelfBg() {
        return ifSelfBg;
    }

    public void setIfSelfBg(boolean ifSelfBg) {
        this.ifSelfBg = ifSelfBg;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public QRCodeTemple(String code, double money, boolean ifOnly, boolean ifShowLogo, boolean ifSelfBg, String arti, int width, int height, int x, int y) {
        this.code = code;
        this.money = money;
        this.ifOnly = ifOnly;
        this.ifShowLogo = ifShowLogo;
        this.ifSelfBg = ifSelfBg;
        this.arti = arti;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public QRCodeTemple() {
    }

}
