package com.lushihao.qrcode.entity.qrcode;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "qrcodetemple")  // 配置文件中的前缀
public class AllQRCodeTemple {

    private List<QRCodeTemple> templeList = new ArrayList<>();

    public List<QRCodeTemple> getTempleList() {
        return templeList;
    }

    public void setTempleList(List<QRCodeTemple> templeList) {
        this.templeList = templeList;
    }

    public QRCodeTemple getItem(String code) {
        List<QRCodeTemple> list = new ArrayList<>();
        list.addAll(templeList);
        if (code != null) {
            list = templeList.stream().filter(s -> s.getCode().equals(code)).collect(Collectors.toList());
            if(list.size() == 1){
                return list.get(0);
            }
        }
        return null;
    }

}
