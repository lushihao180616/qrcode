package com.lushihao.qrcode.entity.image;

public class ImageIcon {

    /**
     * 原图片地址
     */
    private String path;

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

    public ImageIcon() {
    }

    public ImageIcon(String path, String icon, int width, int height, int x, int y) {
        this.path = path;
        this.icon = icon;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

}
