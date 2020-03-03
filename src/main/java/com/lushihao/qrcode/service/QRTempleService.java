package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;

import java.util.List;
import java.util.Map;

public interface QRTempleService {

    Result create(QRCodeTemple qrCodeTemple, String templeItemsPath);

    Result update(QRCodeTemple qrCodeTemple, String templeItemsPath);

    Result delete(String code);

    List<Map<String, Object>> filter(String code);

    String downLoad(String downLoad);
}
