package com.lushihao.qrcode.service.image;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageWaterMark;

public interface ImageWaterMarkService {

    Result addWaterMark(ImageWaterMark imageWaterMark);

    Result testWaterMark(ImageWaterMark imageWaterMark);

}
