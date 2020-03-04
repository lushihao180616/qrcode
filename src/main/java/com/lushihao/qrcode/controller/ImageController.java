package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageWaterMark;
import com.lushihao.qrcode.service.ImageWaterMarkService;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("imageWaterMark")
public class ImageController {

    @Resource
    private ImageWaterMarkService imageWaterMarkService;
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("add")
    @ResponseBody
    public Result addWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        ImageWaterMark wm = transform(reqMap);
        wm.setBusinessCode((String) reqMap.get("businessCode"));
        return imageWaterMarkService.addWaterMark(wm);
    }

    @RequestMapping("test")
    @ResponseBody
    public Result testWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        ImageWaterMark wm = transform(reqMap);
        wm.setManagerCode("00000000");
        return imageWaterMarkService.testWaterMark(wm);
    }

    private ImageWaterMark transform(Map<String, Object> reqMap) {
        ImageWaterMark wm = new ImageWaterMark();
        wm.setPath((String) reqMap.get("path"));
        wm.setHeightPercentage((Integer) reqMap.get("height"));
        wm.setxPercentage((Integer) reqMap.get("x"));
        wm.setyPercentage((Integer) reqMap.get("y"));
        wm.setAlpha((Integer) reqMap.get("alpha"));
        return wm;
    }

}
