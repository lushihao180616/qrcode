package com.lushihao.qrcode.entity.basic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "basic")  // 配置文件中的前缀
public class ProjectBasicInfo {

    /**
     * 模板地址
     */
    private String templeUrl;
    /**
     * 生成的二维码地址
     */
    private String qrcodeUrl;
    /**
     * 商家图标地址
     */
    private String businessUrl;
    /**
     * 模板样例地址
     */
    private String modelUrl;
    /**
     * 输出动图地址
     */
    private String outputGif;
    /**
     * 删除模板的同时也删除模板图片文件
     */
    private boolean deleteAllTempleFiles;
    /**
     * 删除商家的同时也删除商家图片文件
     */
    private boolean deleteAllBusinessFiles;

    public String getTempleUrl() {
        return templeUrl;
    }

    public void setTempleUrl(String templeUrl) {
        this.templeUrl = templeUrl;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getBusinessUrl() {
        return businessUrl;
    }

    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getOutputGif() {
        return outputGif;
    }

    public void setOutputGif(String outputGif) {
        this.outputGif = outputGif;
    }

    public boolean isDeleteAllTempleFiles() {
        return deleteAllTempleFiles;
    }

    public void setDeleteAllTempleFiles(boolean deleteAllTempleFiles) {
        this.deleteAllTempleFiles = deleteAllTempleFiles;
    }

    public boolean isDeleteAllBusinessFiles() {
        return deleteAllBusinessFiles;
    }

    public void setDeleteAllBusinessFiles(boolean deleteAllBusinessFiles) {
        this.deleteAllBusinessFiles = deleteAllBusinessFiles;
    }

    public ProjectBasicInfo(String templeUrl, String qrcodeUrl, String businessUrl, String modelUrl, boolean deleteAllTempleFiles, boolean deleteAllBusinessFiles) {
        this.templeUrl = templeUrl;
        this.qrcodeUrl = qrcodeUrl;
        this.businessUrl = businessUrl;
        this.modelUrl = modelUrl;
        this.deleteAllTempleFiles = deleteAllTempleFiles;
        this.deleteAllBusinessFiles = deleteAllBusinessFiles;
    }

    public ProjectBasicInfo() {
    }

}
