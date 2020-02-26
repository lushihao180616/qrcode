package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.video.VideoWaterMark;

public interface VideoService {

    String create(VideoWaterMark video, String code);

}
