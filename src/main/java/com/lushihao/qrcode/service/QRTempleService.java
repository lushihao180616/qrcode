package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.temple.QRCodeTemple;

import java.util.List;

public interface QRTempleService {

    String create(QRCodeTemple qrCodeTemple, String templeItemsPath);

    String update(QRCodeTemple qrCodeTemple, String templeItemsPath);

    String delete(String code);

    List<QRCodeTemple> filter(String code);
}
