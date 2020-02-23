package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.image.WaterMark;

public interface ImageService {

    String addWaterMark(WaterMark waterMark);

    String testWaterMark(WaterMark waterMark);

}
