package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageIcon;

public interface ImageIconService {

    Result addIcon(ImageIcon imageIcon);

    Result testIcon(ImageIcon imageIcon);

}
