package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.image.WaterMark;

public interface WaterMarkService {

    String addWaterMark(WaterMark waterMark);

    String testWaterMark(WaterMark waterMark);

}
