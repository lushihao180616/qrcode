package com.lushihao.qrcode.service.video;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoFont;

public interface VideoFontService {

    Result addFont(VideoFont videoFont);

    Result testFont(VideoFont videoFont);
    
}
