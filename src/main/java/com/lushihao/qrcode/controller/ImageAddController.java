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
        if ((Integer) reqMap.get("ifShowLogo") == 0) {
            wm.setIfShowLogo(false);
        } else if ((Integer) reqMap.get("ifShowLogo") == 1) {
            wm.setIfShowLogo(true);
        }
        if ((Integer) reqMap.get("ifShowFont") == 0) {
            wm.setIfShowFont(false);
        } else if ((Integer) reqMap.get("ifShowFont") == 1) {
            wm.setIfShowFont(true);
        }
        wm.setWidth((Integer) reqMap.get("width"));
        wm.setHeight((Integer) reqMap.get("height"));
        wm.setX((Integer) reqMap.get("x"));
        wm.setY((Integer) reqMap.get("y"));
        return imageService.addWaterMark(wm);
    }

}
