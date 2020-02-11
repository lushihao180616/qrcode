package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.QRCodeRecordMapper;
import com.lushihao.qrcode.dao.QRTempleMapper;
import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.entity.qrcode.QRCodeVo;
import com.lushihao.qrcode.service.QRCodeService;
import com.lushihao.qrcode.util.LSHQRCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Resource
    private LSHQRCodeUtil lshqrCodeUtil;
    @Resource
    private QRTempleMapper qrTempleMapper;
    @Resource
    private QRCodeRecordMapper qrCodeRecordMapper;

    @Override
    @Transactional
    public String create(QRCodeRequest qrCodeRequest) {
        QRCodeVo qrCodeVo = new QRCodeVo(qrCodeRequest.getMessage(), qrTempleMapper.filter(qrCodeRequest.getTempleCode()).get(0), qrCodeRequest.getBusinessCode(), qrCodeRequest.getFileName(), qrCodeRequest.getBackGround());
        return lshqrCodeUtil.qrcode(qrCodeVo);
    }

    @Override
    @Transactional
    public List<QRCodeRecord> selectRecord(QRCodeRecord qrCodeRecord) {
        return qrCodeRecordMapper.select(qrCodeRecord);
    }

}
