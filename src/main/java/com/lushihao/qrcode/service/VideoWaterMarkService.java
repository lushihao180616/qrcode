package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoWaterMark;

public interface VideoWaterMarkService {

    Result addWaterMark(VideoWaterMark video, String code);

    Result testWaterMark(VideoWaterMark videoWaterMark, String code);

}
