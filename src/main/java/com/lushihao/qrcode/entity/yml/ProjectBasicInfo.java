package com.lushihao.qrcode.entity.yml;

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
     * 生成mp4时临时jpg文件地址
     */
    private String tempJpgUrl;
    /**
     * Ffmpeg安装路径
     */
    private String ffmpegUrl;
    /**
     * 删除模板的同时也删除模板图片文件
     */
    private boolean deleteAllTempleFiles;
    /**
     * 删除商家的同时也删除商家图片文件
     */
    private boolean deleteAllBusinessFiles;
    /**
     * 云存储主机
     */
    private String bucketHost;
    /**
     * 云存储图片
     */
    private String bucketImageDir;
    /**
     * 云存储视频
     */
    private String bucketVideoDir;

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

    public String getTempJpgUrl() {
        return tempJpgUrl;
    }

    public void setTempJpgUrl(String tempJpgUrl) {
        this.tempJpgUrl = tempJpgUrl;
    }

    public String getFfmpegUrl() {
        return ffmpegUrl;
    }

    public void setFfmpegUrl(String ffmpegUrl) {
        this.ffmpegUrl = ffmpegUrl;
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

    public String getBucketHost() {
        return bucketHost;
    }

    public void setBucketHost(String bucketHost) {
        this.bucketHost = bucketHost;
    }

    public String getBucketImageDir() {
        return bucketImageDir;
    }

    public void setBucketImageDir(String bucketImageDir) {
        this.bucketImageDir = bucketImageDir;
    }

    public String getBucketVideoDir() {
        return bucketVideoDir;
    }

    public void setBucketVideoDir(String bucketVideoDir) {
        this.bucketVideoDir = bucketVideoDir;
    }

    public ProjectBasicInfo() {
    }

    public ProjectBasicInfo(String templeUrl, String qrcodeUrl, String businessUrl, String modelUrl, String tempJpgUrl, String ffmpegUrl, boolean deleteAllTempleFiles, boolean deleteAllBusinessFiles, String bucketHost, String bucketImageDir, String bucketVideoDir) {
        this.templeUrl = templeUrl;
        this.qrcodeUrl = qrcodeUrl;
        this.businessUrl = businessUrl;
        this.modelUrl = modelUrl;
        this.tempJpgUrl = tempJpgUrl;
        this.ffmpegUrl = ffmpegUrl;
        this.deleteAllTempleFiles = deleteAllTempleFiles;
        this.deleteAllBusinessFiles = deleteAllBusinessFiles;
        this.bucketHost = bucketHost;
        this.bucketImageDir = bucketImageDir;
        this.bucketVideoDir = bucketVideoDir;
    }

}
