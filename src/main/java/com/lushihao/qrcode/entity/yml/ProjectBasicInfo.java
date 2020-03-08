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
     * ftp ip地址
     */
    private String ftpIp;
    /**
     * ftp 端口
     */
    private int ftpPort;
    /**
     * ftp 用户名
     */
    private String ftpUserName;
    /**
     * ftp 密码
     */
    private String ftpPassword;

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

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public ProjectBasicInfo() {
    }

    public ProjectBasicInfo(String templeUrl, String qrcodeUrl, String businessUrl, String modelUrl, String tempJpgUrl, String ffmpegUrl, boolean deleteAllTempleFiles, boolean deleteAllBusinessFiles, String ftpIp, int ftpPort, String ftpUserName, String ftpPassword) {
        this.templeUrl = templeUrl;
        this.qrcodeUrl = qrcodeUrl;
        this.businessUrl = businessUrl;
        this.modelUrl = modelUrl;
        this.tempJpgUrl = tempJpgUrl;
        this.ffmpegUrl = ffmpegUrl;
        this.deleteAllTempleFiles = deleteAllTempleFiles;
        this.deleteAllBusinessFiles = deleteAllBusinessFiles;
        this.ftpIp = ftpIp;
        this.ftpPort = ftpPort;
        this.ftpUserName = ftpUserName;
        this.ftpPassword = ftpPassword;
    }

}
