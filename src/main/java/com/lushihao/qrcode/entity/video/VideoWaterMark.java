package com.lushihao.qrcode.entity.video;

import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.manager.Manager;

/**
 * 视频类
 */
public class VideoWaterMark {

    /**
     * 商家标识
     */
    private Business business;
    /**
     * 管理员标识
     */
    private Manager manager;
    /**
     * 高度
     */
    private int height;
    /**
     * 原视频地址
     */
    private String path;
    /**
     * 生成视频路径
     */
    private String newPath;
    /**
     * 生成图片路径
     */
    private String imagePath;
    /**
     * x偏移量
     */
    private int x;
    /**
     * y偏移量
     */
    private int y;
    /**
     * 字体颜色
     */
    private String fontColor;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public VideoWaterMark() {
    }

    public VideoWaterMark(Business business, Manager manager, int height, String path, String newPath, String imagePath, int x, int y, String fontColor) {
        this.business = business;
        this.manager = manager;
        this.height = height;
        this.path = path;
        this.newPath = newPath;
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
        this.fontColor = fontColor;
    }

}
