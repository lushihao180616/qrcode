package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.business.AllBusiness;
import com.lushihao.qrcode.entity.qrcode.AllQRCodeTemple;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.service.QRCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("qrcode")
public class QRCodeController {

    @Resource
    private QRCodeService qrCodeService;
    @Resource
    private AllBusiness allBusiness;
    @Resource
    private AllQRCodeTemple allQRCodeTemple;

    @RequestMapping("create")
    public void create(QRCodeRequest qrCodeRequest) {
        qrCodeService.create(qrCodeRequest);
    }

    @RequestMapping("getIndex")
    public String getIndex(HttpServletRequest request) {
        request.setAttribute("business", allBusiness.getInfoList());
        request.setAttribute("temple", allQRCodeTemple.getTempleList());
        return "index";
    }

}
