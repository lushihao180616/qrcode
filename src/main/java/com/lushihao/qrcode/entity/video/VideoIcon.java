package com.lushihao.qrcode.entity.video;

public class VideoIcon {

    /**
     * 原图片地址
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
     * 图标地址
     */
    private String icon;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public VideoIcon() {
    }

    public VideoIcon(String path, String newPath, String imagePath, String icon, int width, int height, int x, int y) {
        this.path = path;
        this.newPath = newPath;
        this.imagePath = imagePath;
        this.icon = icon;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

}
