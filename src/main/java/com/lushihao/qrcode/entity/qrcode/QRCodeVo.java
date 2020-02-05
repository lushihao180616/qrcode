package com.lushihao.qrcode.entity.qrcode;

import com.lushihao.qrcode.entity.temple.QRCodeTemple;

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
    private String businessCode;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 背景图片
     */
    private String backGround;

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

    public QRCodeVo(String message, QRCodeTemple typeCode, String businessCode, String fileName, String backGround) {
        this.message = message;
        this.typeCode = typeCode;
        this.businessCode = businessCode;
        this.fileName = fileName;
        this.backGround = backGround;
    }

    public QRCodeVo() {
    }

}
