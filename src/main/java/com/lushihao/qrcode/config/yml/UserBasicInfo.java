package com.lushihao.qrcode.config.yml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "user")  // 配置文件中的前缀
public class UserBasicInfo {

    /**
     * 用户标识
     */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserBasicInfo() {
    }

    public UserBasicInfo(String code) {
        this.code = code;
    }

}
