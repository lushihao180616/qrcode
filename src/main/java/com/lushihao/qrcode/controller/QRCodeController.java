package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.QRCodeRequest;
import com.lushihao.qrcode.service.QRCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("qrcode")
public class QRCodeController {

    @Resource
    private QRCodeService qrCodeService;

    @RequestMapping("create")
    public void create(QRCodeRequest qrCodeRequest) {
        qrCodeService.create(qrCodeRequest);
    }

}
