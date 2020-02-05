package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.QRCodeRecordMapper;
import com.lushihao.qrcode.dao.QRTempleMapper;
import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.entity.qrcode.QRCodeVo;
import com.lushihao.qrcode.service.QRCodeService;
import com.lushihao.qrcode.util.LSHQRCodeUtil;
import org.springframework.stereotype.Service;

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
    public String create(QRCodeRequest qrCodeRequest) {
        QRCodeVo qrCodeVo = new QRCodeVo(qrCodeRequest.getMessage(), qrTempleMapper.filter(qrCodeRequest.getTempleCode()).get(0), qrCodeRequest.getBusinessCode(), qrCodeRequest.getFileName(), qrCodeRequest.getBackGround());
        boolean back = lshqrCodeUtil.qrcode(qrCodeVo);
        if (back) {
            return "创建成功";
        }
        return "创建失败";
    }

    @Override
    public List<QRCodeRecord> selectRecord(QRCodeRecord qrCodeRecord) {
        return qrCodeRecordMapper.select(qrCodeRecord);
    }

}
