package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.WaterMark;

public interface WaterMarkService {

    Result addWaterMark(WaterMark waterMark);

    Result testWaterMark(WaterMark waterMark);

}
