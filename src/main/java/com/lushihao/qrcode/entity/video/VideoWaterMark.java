package com.lushihao.qrcode.entity.video;

import com.lushihao.qrcode.entity.business.Business;

/**
 * 视频类
 */
public class VideoWaterMark {

    /**
     * 商家标识
     */
    private Business business;
    /**
     * 原视频地址
     */
    private String oldVideoPath;
    /**
     * 生成视频地址
     */
    private String newVideoPath;
    /**
     * 字体x偏移量
     */
    private int fontX;
    /**
     * 字体y偏移量
     */
    private int fontY;
    /**
     * 字体大小
     */
    private int fontSize;
    /**
     * 字体颜色
     */
    private String fontColor;
    /**
     * 字体阴影
     */
    private int fontShadow;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getOldVideoPath() {
        return oldVideoPath;
    }

    public void setOldVideoPath(String oldVideoPath) {
        this.oldVideoPath = oldVideoPath;
    }

    public String getNewVideoPath() {
        return newVideoPath;
    }

    public void setNewVideoPath(String newVideoPath) {
        this.newVideoPath = newVideoPath;
    }

    public int getFontX() {
        return fontX;
    }

    public void setFontX(int fontX) {
        this.fontX = fontX;
    }

    public int getFontY() {
        return fontY;
    }

    public void setFontY(int fontY) {
        this.fontY = fontY;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getFontShadow() {
        return fontShadow;
    }

    public void setFontShadow(int fontShadow) {
        this.fontShadow = fontShadow;
    }

    public VideoWaterMark() {
    }

    public VideoWaterMark(Business business, String oldVideoPath, String newVideoPath, int fontX, int fontY, int fontSize, String fontColor, int fontShadow) {
        this.business = business;
        this.oldVideoPath = oldVideoPath;
        this.newVideoPath = newVideoPath;
        this.fontX = fontX;
        this.fontY = fontY;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.fontShadow = fontShadow;
    }

}
