package com.lushihao.qrcode.entity.temple;

public class TempleAnalysis {

    /**
     * 模板标识
     */
    private String templeCode;
    /**
     * 销售量
     */
    private int useCount;
    /**
     * 销售额
     */
    private int totalMoney;
    /**
     * 使用该二维码的商家数量
     */
    private int businessCount;

    public String getTempleCode() {
        return templeCode;
    }

    public void setTempleCode(String templeCode) {
        this.templeCode = templeCode;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getBusinessCount() {
        return businessCount;
    }

    public void setBusinessCount(int businessCount) {
        this.businessCount = businessCount;
    }

}
