package com.lushihao.qrcode.entity.qrcode;

public class QRCodeRequest {

    /**
     * 二维码信息
     */
    private String message;
    /**
     * 二维码类型
     */
    private String type;
    /**
     * 二维码所属编号
     */
    private String templeCode;
    /**
     * 商家
     */
    private String businessCode;
    /**
     * 管理员
     */
    private String managerCode;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 背景图片
     */
    private String backGround;
    /**
     * 背景图片
     */
    private int shortLength;
    /**
     * 背景图片
     */
    private int x;
    /**
     * 背景图片
     */
    private int y;
    /**
     * 码透明度
     */
    private int alpha;
    /**
     * 旋转角度
     */
    private int angle;
    /**
     * 金豆
     */
    private int bean;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTempleCode() {
        return templeCode;
    }

    public void setTempleCode(String templeCode) {
        this.templeCode = templeCode;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public int getShortLength() {
        return shortLength;
    }

    public void setShortLength(int shortLength) {
        this.shortLength = shortLength;
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

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getBean() {
        return bean;
    }

    public void setBean(int bean) {
        this.bean = bean;
    }

}
