package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;

import java.util.List;
import java.util.Map;

public interface QRCodeService {

    Map<String, String> create(QRCodeRequest qrCodeRequest);

    Map<String, String> test(QRCodeRequest qrCodeRequest);

    List<QRCodeRecord> selectRecord(QRCodeRecord qrCodeRecord);

}
