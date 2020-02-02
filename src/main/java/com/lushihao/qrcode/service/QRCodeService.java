package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;

import java.util.List;

public interface QRCodeService {

    String create(QRCodeRequest qrCodeRequest);

    List<QRCodeRecord> selectRecord(QRCodeRecord qrCodeRecord);

}
