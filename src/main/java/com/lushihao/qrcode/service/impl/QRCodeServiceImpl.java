package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.entity.QRCodeVo;
import com.lushihao.qrcode.service.QRCodeService;
import com.lushihao.qrcode.util.LSHQRCodeUtil;

public class QRCodeServiceImpl implements QRCodeService {

    public boolean create(QRCodeVo qrCodeVo) {
        return LSHQRCodeUtil.qrcode(qrCodeVo);
    }

}
