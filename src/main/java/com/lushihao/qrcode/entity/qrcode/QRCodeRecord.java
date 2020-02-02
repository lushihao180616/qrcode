package com.lushihao.qrcode.entity.qrcode;

public class QRCodeRecord {

    /**
     * 模板标识
     */
    private String templeCode;
    /**
     * 商家标识
     */
    private String businessCode;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件地址
     */
    private String url;
    /**
     * 保存时间
     */
    private String saveTime;
    /**
     * 保存时间
     */
    private double money;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

}
