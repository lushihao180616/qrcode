package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.service.QRCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("qrcode")
public class QRCodeController {

    @Resource
    private QRCodeService qrCodeService;

    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody Map<String, Object> reqMap) {
        QRCodeRequest qrCodeRequest = LSHMapUtils.mapToEntity(reqMap, QRCodeRequest.class);
        return qrCodeService.create(qrCodeRequest);
    }

    @RequestMapping("selectRecord")
    @ResponseBody
    public List<QRCodeRecord> selectRecord(@RequestBody Map<String, Object> reqMap) {
        QRCodeRecord qrCodeRecord = LSHMapUtils.mapToEntity(reqMap, QRCodeRecord.class);
        return qrCodeService.selectRecord(qrCodeRecord);
    }

}