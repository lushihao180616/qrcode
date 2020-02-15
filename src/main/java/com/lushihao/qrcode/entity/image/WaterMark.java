package com.lushihao.qrcode.entity.image;

public class WaterMark {

    /**
     * 文件地址
     */
    private String path;
    /**
     * 商家标识
     */
    private String businessCode;
    /**
     * 商标
     */
    private boolean ifShowLogo;
    /**
     * 是否显示底部文字
     */
    private boolean ifShowFont;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * x偏移量
     */
    private int x;
    /**
     * y偏移量
     */
    private int y;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public boolean isIfShowLogo() {
        return ifShowLogo;
    }

    public void setIfShowLogo(boolean ifShowLogo) {
        this.ifShowLogo = ifShowLogo;
    }

    public boolean isIfShowFont() {
        return ifShowFont;
    }

    public void setIfShowFont(boolean ifShowFont) {
        this.ifShowFont = ifShowFont;
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

}
