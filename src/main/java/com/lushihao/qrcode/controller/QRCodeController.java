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
    private QRTempleService qrTempleService;
    @Resource
    private BusinessService businessService;
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("create")
    @ResponseBody
    public Result create(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        return qrCodeService.create(transform(reqMap));
    }

    @RequestMapping("test")
    @ResponseBody
    public Result test(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        return qrCodeService.test(transform(reqMap));
    }

    @RequestMapping("selectRecord")
    @ResponseBody
    public Result selectRecord(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        return new Result(true, qrCodeService.selectRecord(LSHMapUtils.mapToEntity(reqMap, QRCodeRecord.class)), "搜索完成", null);
    }

    @RequestMapping("qrcodeInit")
    @ResponseBody
    public Result qrcodeInit(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("record", qrCodeService.selectRecord(new QRCodeRecord()));
        map.put("temple", qrTempleService.filter(null));
        map.put("business", businessService.filter(new Business()));
        Result result = new Result(true, map, "搜索完成", null);
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
