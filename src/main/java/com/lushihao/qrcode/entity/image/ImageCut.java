package com.lushihao.qrcode.entity.image;

public class ImageCut {

    /**
     * 原图片地址
     */
    private String path;
    /**
     * 商家标识
     */
    private String businessCode;
    /**
     * 管理员标识
     */
    private String managerCode;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
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

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
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

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public ImageCut() {
    }

    public ImageCut(String path, String businessCode, String managerCode, int width, int height, int alpha) {
        this.path = path;
        this.businessCode = businessCode;
        this.managerCode = managerCode;
        this.width = width;
        this.height = height;
        this.alpha = alpha;
    }

}
