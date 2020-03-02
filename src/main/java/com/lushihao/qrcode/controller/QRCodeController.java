package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.service.BusinessService;
import com.lushihao.qrcode.service.QRCodeService;
import com.lushihao.qrcode.service.QRTempleService;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("qrcode")
public class QRCodeController {

    @Resource
    private QRCodeService qrCodeService;
    @Resource
    private LSHMACUtil lshmacUtil;
    @Resource
    private QRTempleService qrTempleService;
    @Resource
    private BusinessService businessService;

    @RequestMapping("create")
    @ResponseBody
    public Result create(@RequestBody Map<String, Object> reqMap) {
//        if (!lshmacUtil.check()) {
//            return null;
//        }
        QRCodeRequest qrCodeRequest = transform(reqMap);
        Result result = new Result(true, qrCodeService.create(qrCodeRequest), null, null);
        return result;
    }

    @RequestMapping("test")
    @ResponseBody
    public Result test(@RequestBody Map<String, Object> reqMap) {
//        if (!lshmacUtil.check()) {
//            return null;
//        }
        QRCodeRequest qrCodeRequest = transform(reqMap);
        Result result = new Result(true, qrCodeService.test(qrCodeRequest), null, null);
        return result;
    }

    @RequestMapping("selectRecord")
    @ResponseBody
    public Result selectRecord(@RequestBody Map<String, Object> reqMap) {
//        if (!lshmacUtil.check()) {
//            return null;
//        }
        QRCodeRecord qrCodeRecord = LSHMapUtils.mapToEntity(reqMap, QRCodeRecord.class);
        Result result = new Result(true, qrCodeService.selectRecord(qrCodeRecord), null, null);
        return result;
    }

    @RequestMapping("qrcodeInit")
    @ResponseBody
    public Result qrcodeInit(@RequestBody Map<String, Object> reqMap) {
        Map<String, Object> map = new HashMap<>();
//        if (!lshmacUtil.check()) {
//            return null;
//        }
        map.put("record", qrCodeService.selectRecord(new QRCodeRecord()));
        map.put("temple", qrTempleService.filter(""));
        map.put("business", businessService.filter(new Business()));
        Result result = new Result(true, map, null, null);
        return result;
    }

    private QRCodeRequest transform(Map<String, Object> reqMap) {
        QRCodeRequest qrCodeRequest = LSHMapUtils.mapToEntity(reqMap, QRCodeRequest.class);
        if (reqMap.get("shortLength") == null || "".equals(reqMap.get("shortLength"))) {
            qrCodeRequest.setShortLength(0);
        } else {
            qrCodeRequest.setShortLength((Integer) reqMap.get("shortLength"));
        }
        if (reqMap.get("x") == null || "".equals(reqMap.get("x"))) {
            qrCodeRequest.setX(0);
        } else {
            qrCodeRequest.setX((Integer) reqMap.get("x"));
        }
        if (reqMap.get("y") == null || "".equals(reqMap.get("y"))) {
            qrCodeRequest.setY(0);
        } else {
            qrCodeRequest.setY((Integer) reqMap.get("y"));
        }
        if (reqMap.get("alpha") == null || "".equals(reqMap.get("alpha"))) {
            qrCodeRequest.setAlpha(0);
        } else {
            qrCodeRequest.setAlpha((Integer) reqMap.get("alpha"));
        }
        if (reqMap.get("angle") == null || "".equals(reqMap.get("angle"))) {
            qrCodeRequest.setAngle(0);
        } else {
            qrCodeRequest.setAngle((Integer) reqMap.get("angle"));
        }
        return qrCodeRequest;
    }

}
