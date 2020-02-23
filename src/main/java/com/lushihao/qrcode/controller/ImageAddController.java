package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.image.WaterMark;
import com.lushihao.qrcode.service.ImageService;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("waterMark")
public class ImageAddController {

    @Resource
    private ImageService imageService;
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("add")
    @ResponseBody
    public String addWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        WaterMark wm = new WaterMark();
        wm.setBusinessCode((String) reqMap.get("businessCode"));
        wm.setPath((String) reqMap.get("path"));
        wm.setHeightPercentage((Integer) reqMap.get("height"));
        wm.setxPercentage((Integer) reqMap.get("x"));
        wm.setyPercentage((Integer) reqMap.get("y"));
        wm.setAlpha((Integer) reqMap.get("alpha"));
        return imageService.addWaterMark(wm);
    }

    @RequestMapping("test")
    @ResponseBody
    public String testWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        WaterMark wm = new WaterMark();
        wm.setBusinessCode((String) reqMap.get("businessCode"));
        wm.setPath((String) reqMap.get("path"));
        wm.setHeightPercentage((Integer) reqMap.get("height"));
        wm.setxPercentage((Integer) reqMap.get("x"));
        wm.setyPercentage((Integer) reqMap.get("y"));
        wm.setAlpha((Integer) reqMap.get("alpha"));
        return imageService.testWaterMark(wm);
    }

}
