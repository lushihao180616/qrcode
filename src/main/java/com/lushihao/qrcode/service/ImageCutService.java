package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageCut;

public interface ImageCutService {

    Result addCut(ImageCut imageCut);

    Result testCut(ImageCut imageCut);

}
