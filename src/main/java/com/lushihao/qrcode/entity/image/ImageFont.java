package com.lushihao.qrcode.entity.image;

public class ImageFont {

    /**
     * 原图片地址
     */
    private String path;
    /**
     * x偏移量
     */
    private int x;
    /**
     * y偏移量
     */
    private int y;
    /**
     * 字体大小
     */
    private int size;
    /**
     * 布局
     */
    private String layout;
    /**
     * 信息
     */
    private String message;
    /**
     * 字体颜色
     */
    private String color;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ImageFont() {
    }

    public ImageFont(String path, int x, int y, int size, String layout, String message, String color) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.size = size;
        this.layout = layout;
        this.message = message;
        this.color = color;
    }

}
