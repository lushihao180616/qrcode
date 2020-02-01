package com.lushihao.qrcode.config.yml;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class YmlInitConfig {

    /**
     * 配置yml文件
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(
                new ClassPathResource("business-info.yml"),
                new ClassPathResource("project-basic.yml"),
                new ClassPathResource("qrcode-temple.yml")
        );
        configurer.setProperties(yaml.getObject());
        return configurer;
    }

}
