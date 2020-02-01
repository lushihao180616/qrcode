package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.business.AllBusiness;
import com.lushihao.qrcode.entity.qrcode.AllQRCodeTemple;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.service.QRCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    @ResponseBody
    public String create(@RequestBody Map<String, Object> reqMap) {
        QRCodeRequest qrCodeRequest = LSHMapUtils.mapToEntity(reqMap, QRCodeRequest.class);
        boolean ifsuccess = qrCodeService.create(qrCodeRequest);
        if (ifsuccess) {
            return "创建成功";
        } else {
            return "创建失败";
        }
    }

    @RequestMapping("getIndex")
    public String getIndex(HttpServletRequest request) {
        request.setAttribute("business", allBusiness.getInfoList());
        request.setAttribute("temple", allQRCodeTemple.getTempleList());

        return "index";
    }

}
