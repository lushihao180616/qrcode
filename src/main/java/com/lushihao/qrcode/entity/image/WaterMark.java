package com.lushihao.qrcode.entity.image;

public class WaterMark {

    /**
     * 原图片地址
     */
    private String path;
    /**
     * 商家标识
     */
    private String businessCode;
    /**
     * 水印高度百分比（0-100）
     */
    private int heightPercentage;
    /**
     * x偏移百分比（0-100）
     */
    private int xPercentage;
    /**
     * y偏移百分比（0-100）
     */
    private int yPercentage;
    /**
     * 透明度
     */
    private int alpha;

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

    public int getHeightPercentage() {
        return heightPercentage;
    }

    public void setHeightPercentage(int heightPercentage) {
        this.heightPercentage = heightPercentage;
    }

    public int getxPercentage() {
        return xPercentage;
    }

    public void setxPercentage(int xPercentage) {
        this.xPercentage = xPercentage;
    }

    public int getyPercentage() {
        return yPercentage;
    }

    public void setyPercentage(int yPercentage) {
        this.yPercentage = yPercentage;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
    
}