package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.entity.AllQRCodeTemple;
import com.lushihao.qrcode.entity.QRCodeTemple;
import com.lushihao.qrcode.entity.QRCodeVo;
import com.lushihao.qrcode.service.QRCodeService;
import com.lushihao.qrcode.util.LSHQRCodeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Resource
    private LSHQRCodeUtil lshqrCodeUtil;
    @Resource
    private AllQRCodeTemple allQRCodeTemple;

    @Override
    public boolean create() {
        QRCodeVo qrCodeVo = new QRCodeVo("卢世豪", allQRCodeTemple.getItem("A0000"), "flower", "001");
        lshqrCodeUtil.qrcode(qrCodeVo);
        QRCodeVo qrCodeVo2 = new QRCodeVo("卢世豪", allQRCodeTemple.getItem("A0001"), "flower", "001");
        return lshqrCodeUtil.qrcode(qrCodeVo2);
    }

}
