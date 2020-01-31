package com.lushihao.qrcode.entity;

/**
 * 二维码的使用类
 */
public class QRCodeVo {

    /**
     * 二维码信息
     */
    private String message;
    /**
     * 二维码所属编号
     */
    private QRCodeTemple typeCode;
    /**
     * 商家
     */
    private String businessName;
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

    public QRCodeTemple getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(QRCodeTemple typeCode) {
        this.typeCode = typeCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public QRCodeVo(String message, QRCodeTemple typeCode, String businessName, String fileName) {
        this.message = message;
        this.typeCode = typeCode;
        this.businessName = businessName;
        this.fileName = fileName;
    }

    public QRCodeVo() {
    }

}
