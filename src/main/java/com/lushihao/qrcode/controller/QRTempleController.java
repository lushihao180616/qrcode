package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;
import com.lushihao.qrcode.service.QRTempleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("temple")
public class QRTempleController {

    @Resource
    private QRTempleService qrTempleService;
    @Resource
    private CheckController checkController;

    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody Map<String, Object> reqMap) {
        if(checkController.check().equals("0")){
            return null;
        }
        String templeItemsPath = (String) reqMap.get("templeItemsPath");
        QRCodeTemple qrCodeTemple = LSHMapUtils.mapToEntity(reqMap, QRCodeTemple.class);
        String code = (String) reqMap.get("code");
        if (code.charAt(0) == 'J') {
            qrCodeTemple.setIfGif(false);
        } else if (code.charAt(0) == 'D') {
            qrCodeTemple.setIfGif(true);
        }
        if (code.charAt(1) == 'W') {
            qrCodeTemple.setIfShowLogo(false);
        } else if (code.charAt(1) == 'Y') {
            qrCodeTemple.setIfShowLogo(true);
        }
        if (code.charAt(2) == '0') {
            qrCodeTemple.setIfOnly(true);
        } else if (code.charAt(2) == '1') {
            qrCodeTemple.setIfOnly(false);
        }
        if (code.charAt(3) == '0') {
            qrCodeTemple.setArti("0-1-2-3-4");
        } else if (code.charAt(3) == '1') {
            qrCodeTemple.setArti("0-1-2-5-6");
        }
        if (code.charAt(2) == '1' && code.charAt(4) == '1') {
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
        qrCodeTemple.setIconNum((Integer) reqMap.get("iconNum"));
        if (reqMap.get("angle") == null || "".equals(reqMap.get("angle"))) {
            qrCodeTemple.setAngle(0);
        } else {
            qrCodeTemple.setAngle((Integer) reqMap.get("angle"));
        }
        if (reqMap.get("multiple") == null) {
            qrCodeTemple.setMultiple(0);
        } else {
            qrCodeTemple.setMultiple((Integer) reqMap.get("multiple"));
        }
        if (qrCodeTemple.isIfGif()) {
            qrCodeTemple.setIfGif(true);
            String frame = (String) reqMap.get("frame");
            if (frame == null || "".equals(frame)) {
                qrCodeTemple.setFrame(8);
                qrCodeTemple.setStartQRFrame(0);
                qrCodeTemple.setEndQRFrame(0);
            } else {
                String[] frames = frame.split("/");
                qrCodeTemple.setFrame(Integer.valueOf(frames[0]));
                if (frames.length > 1) {
                    qrCodeTemple.setStartQRFrame(Integer.valueOf(frames[1]));
                }
                if (frames.length > 2) {
                    qrCodeTemple.setEndQRFrame(Integer.valueOf(frames[2]));
                }
            }
        } else {
            qrCodeTemple.setIfGif(false);
            qrCodeTemple.setFrame(0);
            qrCodeTemple.setStartQRFrame(0);
            qrCodeTemple.setEndQRFrame(0);
        }
        String back = qrTempleService.create(qrCodeTemple, templeItemsPath);
        return back;
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody Map<String, Object> reqMap) {
        if(checkController.check().equals("0")){
            return null;
        }
        String templeItemsPath = (String) reqMap.get("templeItemsPath");
        QRCodeTemple qrCodeTemple = LSHMapUtils.mapToEntity(reqMap, QRCodeTemple.class);
        String code = (String) reqMap.get("code");
        if (code.charAt(0) == 'J') {
            qrCodeTemple.setIfGif(false);
        } else if (code.charAt(0) == 'D') {
            qrCodeTemple.setIfGif(true);
        }
        if (code.charAt(1) == 'W') {
            qrCodeTemple.setIfShowLogo(false);
        } else if (code.charAt(1) == 'Y') {
            qrCodeTemple.setIfShowLogo(true);
        }
        if (code.charAt(2) == '0') {
            qrCodeTemple.setIfOnly(true);
        } else if (code.charAt(2) == '1') {
            qrCodeTemple.setIfOnly(false);
        }
        if (code.charAt(3) == '0') {
            qrCodeTemple.setArti("0-1-2-3-4");
        } else if (code.charAt(3) == '1') {
            qrCodeTemple.setArti("0-1-2-5-6");
        }
        if (code.charAt(2) == '1' && code.charAt(4) == '1') {
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
        qrCodeTemple.setIconNum((Integer) reqMap.get("iconNum"));
        if (reqMap.get("angle") == null || "".equals(reqMap.get("angle"))) {
            qrCodeTemple.setAngle(0);
        } else {
            qrCodeTemple.setAngle((Integer) reqMap.get("angle"));
        }
        if (reqMap.get("multiple") == null) {
            qrCodeTemple.setMultiple(0);
        } else {
            qrCodeTemple.setMultiple((Integer) reqMap.get("multiple"));
        }
        if (qrCodeTemple.isIfGif()) {
            qrCodeTemple.setIfGif(true);
            String frame = (String) reqMap.get("frame");
            if (frame == null || "".equals(frame)) {
                qrCodeTemple.setFrame(8);
                qrCodeTemple.setStartQRFrame(0);
                qrCodeTemple.setEndQRFrame(0);
            } else {
                String[] frames = frame.split("/");
                qrCodeTemple.setFrame(Integer.valueOf(frames[0]));
                if (frames.length > 1) {
                    qrCodeTemple.setStartQRFrame(Integer.valueOf(frames[1]));
                }
                if (frames.length > 2) {
                    qrCodeTemple.setEndQRFrame(Integer.valueOf(frames[2]));
                }
            }
        } else {
            qrCodeTemple.setIfGif(false);
            qrCodeTemple.setFrame(0);
            qrCodeTemple.setStartQRFrame(0);
            qrCodeTemple.setEndQRFrame(0);
        }
        String back = qrTempleService.update(qrCodeTemple, templeItemsPath);
        return back;
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> reqMap) {
        if(checkController.check().equals("0")){
            return null;
        }
        String code = (String) reqMap.get("code");
        String back = qrTempleService.delete(code);
        return back;
    }

    @RequestMapping("filter")
    @ResponseBody
    public List<Map<String, Object>> filter(@RequestBody Map<String, Object> reqMap) {
        if(checkController.check().equals("0")){
            return null;
        }
        String code = (String) reqMap.get("code");
        if ("".equals(code)) {
            code = null;
        }
        return qrTempleService.filter(code);
    }

}
