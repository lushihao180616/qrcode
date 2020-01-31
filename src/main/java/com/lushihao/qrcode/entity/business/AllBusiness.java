package com.lushihao.qrcode.entity.business;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "businessinfo")  // 配置文件中的前缀
public class AllBusiness {

    private List<Business> infoList = new ArrayList<>();

    public List<Business> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Business> infoList) {
        this.infoList = infoList;
    }

    public Business getItem(String code) {
        List<Business> list = new ArrayList<>();
        list.addAll(infoList);
        if (code != null) {
            list = infoList.stream().filter(s -> s.getCode().equals(code)).collect(Collectors.toList());
            if(list.size() == 1){
                return list.get(0);
            }
        }
        return null;
    }

}
