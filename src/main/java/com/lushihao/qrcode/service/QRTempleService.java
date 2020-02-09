package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.temple.QRCodeTemple;

import java.util.List;
import java.util.Map;

public interface QRTempleService {

    String create(QRCodeTemple qrCodeTemple, String templeItemsPath);

    String update(QRCodeTemple qrCodeTemple, String templeItemsPath);

    String delete(String code);

    List<Map<String, Object>> filter(String code);

    String downLoad(String downLoad);
}
