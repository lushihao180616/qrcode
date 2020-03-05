package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoWaterMark;

public interface VideoWaterMarkService {

    Result create(VideoWaterMark video, String code);

    Result test(VideoWaterMark videoWaterMark, String code);

}
