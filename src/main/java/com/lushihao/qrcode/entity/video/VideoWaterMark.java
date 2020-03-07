package com.lushihao.qrcode.entity.video;

/**
 * 视频类
 */
public class VideoWaterMark {

    /**
     * 商家标识
     */
    private String businessCode;
    /**
     * 管理员标识
     */
    private String managerCode;
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

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
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

    public VideoWaterMark(String businessCode, String managerCode, int height, String path, String newPath, String imagePath, int x, int y, String fontColor) {
        this.businessCode = businessCode;
        this.managerCode = managerCode;
        this.height = height;
        this.path = path;
        this.newPath = newPath;
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
        this.fontColor = fontColor;
    }

}
