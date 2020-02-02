package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.qrcode.QRCodeTemple;

import java.util.List;

public interface QRTempleService {

    String create(QRCodeTemple qrCodeTemple, String templeItemsPath);

    List<QRCodeTemple> filter(String code);

}
