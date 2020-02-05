package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;
import com.lushihao.qrcode.service.QRTempleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("temple")
public class QRTempleController {

    @Resource
    private QRTempleService qrTempleService;

    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody Map<String, Object> reqMap) {
        String templeItemsPath = (String) reqMap.get("templeItemsPath");
        QRCodeTemple qrCodeTemple = LSHMapUtils.mapToEntity(reqMap, QRCodeTemple.class);
        String code = (String) reqMap.get("code");
        if (code.charAt(0) == 'A') {
            qrCodeTemple.setIfShowLogo(false);
        } else if (code.charAt(0) == 'B') {
            qrCodeTemple.setIfShowLogo(true);
        }
        if (code.charAt(1) == '0') {
            qrCodeTemple.setIfOnly(true);
        } else if (code.charAt(1) == '1') {
            qrCodeTemple.setIfOnly(false);
        }
        if (code.charAt(2) == '0') {
            qrCodeTemple.setArti("0");
        } else if (code.charAt(2) == '1') {
            qrCodeTemple.setArti("1");
        }
        if (code.charAt(1) == '1' && code.charAt(3) == '0') {
            qrCodeTemple.setIfSelfBg(true);
        } else {
            qrCodeTemple.setIfSelfBg(false);
        }
        qrCodeTemple.setMoney(Double.parseDouble(reqMap.get("money").toString()));
        if (reqMap.get("width") == null) {
            qrCodeTemple.setWidth(0);
        } else {
            qrCodeTemple.setWidth((Integer) reqMap.get("width"));
        }
        if (reqMap.get("height") == null) {
            qrCodeTemple.setHeight(0);
        } else {
            qrCodeTemple.setHeight((Integer) reqMap.get("height"));
        }
        if (reqMap.get("x") == null) {
            qrCodeTemple.setX(0);
        } else {
            qrCodeTemple.setX((Integer) reqMap.get("x"));
        }
        if (reqMap.get("y") == null) {
            qrCodeTemple.setY(0);
        } else {
            qrCodeTemple.setY((Integer) reqMap.get("y"));
        }
        String back = qrTempleService.create(qrCodeTemple, templeItemsPath);
        return back;
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody Map<String, Object> reqMap) {
        String templeItemsPath = (String) reqMap.get("templeItemsPath");
        QRCodeTemple qrCodeTemple = LSHMapUtils.mapToEntity(reqMap, QRCodeTemple.class);
        String code = (String) reqMap.get("code");
        if (code.charAt(0) == 'A') {
            qrCodeTemple.setIfShowLogo(false);
        } else if (code.charAt(0) == 'B') {
            qrCodeTemple.setIfShowLogo(true);
        }
        if (code.charAt(1) == '0') {
            qrCodeTemple.setIfOnly(true);
        } else if (code.charAt(1) == '1') {
            qrCodeTemple.setIfOnly(false);
        }
        if (code.charAt(2) == '0') {
            qrCodeTemple.setArti("0");
        } else if (code.charAt(2) == '1') {
            qrCodeTemple.setArti("1");
        }
        if (code.charAt(1) == '1' && code.charAt(3) == '0') {
            qrCodeTemple.setIfSelfBg(true);
        } else {
            qrCodeTemple.setIfSelfBg(false);
        }
        qrCodeTemple.setMoney(Double.parseDouble(reqMap.get("money").toString()));
        if (reqMap.get("width") == null) {
            qrCodeTemple.setWidth(0);
        } else {
            qrCodeTemple.setWidth((Integer) reqMap.get("width"));
        }
        if (reqMap.get("height") == null) {
            qrCodeTemple.setHeight(0);
        } else {
            qrCodeTemple.setHeight((Integer) reqMap.get("height"));
        }
        if (reqMap.get("x") == null) {
            qrCodeTemple.setX(0);
        } else {
            qrCodeTemple.setX((Integer) reqMap.get("x"));
        }
        if (reqMap.get("y") == null) {
            qrCodeTemple.setY(0);
        } else {
            qrCodeTemple.setY((Integer) reqMap.get("y"));
        }
        String back = qrTempleService.update(qrCodeTemple, templeItemsPath);
        return back;
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> reqMap) {
        String code = (String) reqMap.get("code");
        String back = qrTempleService.delete(code);
        return back;
    }

    @RequestMapping("filter")
    @ResponseBody
    public List<QRCodeTemple> filter(@RequestBody Map<String, Object> reqMap) {
        String code = (String) reqMap.get("code");
        if ("".equals(code)) {
            code = null;
        }
        return qrTempleService.filter(code);
    }

}
