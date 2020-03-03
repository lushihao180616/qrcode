package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.WaterMark;
import com.lushihao.qrcode.service.WaterMarkService;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("waterMark")
public class WaterMarkController {

    @Resource
    private WaterMarkService waterMarkService;
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("add")
    @ResponseBody
    public Result addWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        WaterMark wm = transform(reqMap);
        wm.setBusinessCode((String) reqMap.get("businessCode"));
        return waterMarkService.addWaterMark(wm);
    }

    @RequestMapping("test")
    @ResponseBody
    public Result testWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        WaterMark wm = transform(reqMap);
        wm.setManagerCode("00000000");
        return waterMarkService.testWaterMark(wm);
    }

    private WaterMark transform(Map<String, Object> reqMap) {
        WaterMark wm = new WaterMark();
        wm.setPath((String) reqMap.get("path"));
        wm.setHeightPercentage((Integer) reqMap.get("height"));
        wm.setxPercentage((Integer) reqMap.get("x"));
        wm.setyPercentage((Integer) reqMap.get("y"));
        wm.setAlpha((Integer) reqMap.get("alpha"));
        return wm;
    }

}
