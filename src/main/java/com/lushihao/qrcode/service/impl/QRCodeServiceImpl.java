package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.entity.qrcode.AllQRCodeTemple;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.entity.qrcode.QRCodeTemple;
import com.lushihao.qrcode.entity.qrcode.QRCodeVo;
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
    public boolean create(QRCodeRequest qrCodeRequest) {
        QRCodeVo qrCodeVo = new QRCodeVo(qrCodeRequest.getMessage(), allQRCodeTemple.getItem(qrCodeRequest.getTempleCode()), qrCodeRequest.getBusinessCode(), qrCodeRequest.getFileName());
        allQRCodeTemple.getTempleList().add(new QRCodeTemple());
        return lshqrCodeUtil.qrcode(qrCodeVo);
    }

}
