package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.BusinessService;
import com.lushihao.qrcode.service.QRCodeService;
import com.lushihao.qrcode.service.QRTempleService;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private ProjectBasicInfo projectBasicInfo;

    @RequestMapping("create")
    @ResponseBody
    public Map<String, String> create(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
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
        return qrCodeService.create(qrCodeRequest);
    }

    @RequestMapping("test")
    @ResponseBody
    public Map<String, String> test(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
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
        return qrCodeService.test(qrCodeRequest);
    }

    @RequestMapping("selectRecord")
    @ResponseBody
    public List<QRCodeRecord> selectRecord(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        QRCodeRecord qrCodeRecord = LSHMapUtils.mapToEntity(reqMap, QRCodeRecord.class);
        return qrCodeService.selectRecord(qrCodeRecord);
    }

    @RequestMapping("qrcodeInit")
    @ResponseBody
    public Map<String, Object> qrcodeInit(@RequestBody Map<String, Object> reqMap) {
        Map<String, Object> map = new HashMap<>();
        if (!lshmacUtil.check()) {
            return null;
        }
        map.put("record", qrCodeService.selectRecord(new QRCodeRecord()));
        map.put("temple", qrTempleService.filter(""));
        map.put("business", businessService.filter(new Business()));
        return map;
    }

}
