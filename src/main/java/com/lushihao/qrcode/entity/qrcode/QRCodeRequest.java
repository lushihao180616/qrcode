package com.lushihao.qrcode.entity.qrcode;

public class QRCodeRequest {

    /**
     * 二维码信息
     */
    private String message;
    /**
     * 二维码所属编号
     */
    private String templeCode;
    /**
     * 商家
     */
    private String businessCode;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public QRCodeRequest(String message, String templeCode, String businessCode, String fileName, String backGround, int shortLength, int x, int y, int alpha, int angle) {
        this.message = message;
        this.templeCode = templeCode;
        this.businessCode = businessCode;
        this.fileName = fileName;
        this.backGround = backGround;
        this.shortLength = shortLength;
        this.x = x;
        this.y = y;
        this.alpha = alpha;
        this.angle = angle;
    }

    public QRCodeRequest() {
    }
}
