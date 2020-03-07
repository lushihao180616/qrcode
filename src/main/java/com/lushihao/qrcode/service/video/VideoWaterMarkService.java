package com.lushihao.qrcode.service.video;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoWaterMark;

public interface VideoWaterMarkService {

    Result addWaterMark(VideoWaterMark videoWaterMark);

    Result testWaterMark(VideoWaterMark videoWaterMark);

}
