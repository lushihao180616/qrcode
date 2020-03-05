package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageFont;

public interface ImageFontService {

    Result addFont(ImageFont imageFont);

    Result testFont(ImageFont imageFont);

}
