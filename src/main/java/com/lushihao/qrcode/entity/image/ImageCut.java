package com.lushihao.qrcode.entity.image;

public class ImageCut {

    /**
     * 原图片地址
     */
    private String path;
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

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public ImageCut() {
    }

    public ImageCut(String path, int width, int height, int x, int y, int alpha) {
        this.path = path;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.alpha = alpha;
    }

}
