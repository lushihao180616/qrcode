package com.lushihao.qrcode.entity;

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

    public ProjectBasicInfo(String templeUrl, String qrcodeUrl) {
        this.templeUrl = templeUrl;
        this.qrcodeUrl = qrcodeUrl;
    }

    public ProjectBasicInfo() {
    }

}
