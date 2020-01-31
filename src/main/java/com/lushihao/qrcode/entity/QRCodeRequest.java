package com.lushihao.qrcode.entity;

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

}
