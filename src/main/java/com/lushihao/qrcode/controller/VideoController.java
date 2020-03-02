package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoWaterMark;
import com.lushihao.qrcode.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("video")
public class VideoController {

    @Resource
    private VideoService videoService;

    @RequestMapping("add")
    @ResponseBody
    public Result create(@RequestBody Map<String, Object> reqMap) {
        VideoWaterMark videoWaterMark = transform(reqMap);
        String code = (String) reqMap.get("businessCode");
        return videoService.create(videoWaterMark, code);
    }

    @RequestMapping("test")
    @ResponseBody
    public Result test(@RequestBody Map<String, Object> reqMap) {
        VideoWaterMark videoWaterMark = transform(reqMap);
        String code = "00000000";
        return videoService.test(videoWaterMark, code);
    }

    private VideoWaterMark transform(Map<String, Object> reqMap) {
        VideoWaterMark videoWaterMark = new VideoWaterMark();
        videoWaterMark.setOldVideoPath((String) reqMap.get("path"));
        if (reqMap.get("x") == null || "".equals(reqMap.get("x"))) {
            videoWaterMark.setFontX(0);
        } else {
            videoWaterMark.setFontX((Integer) reqMap.get("x"));
        }
        if (reqMap.get("y") == null || "".equals(reqMap.get("y"))) {
            videoWaterMark.setFontY(0);
        } else {
            videoWaterMark.setFontY((Integer) reqMap.get("y"));
        }
        videoWaterMark.setFontSize((Integer) reqMap.get("fontSize"));
        videoWaterMark.setFontColor((String) reqMap.get("fontColor"));
        if (reqMap.get("fontShadow").equals("1")) {
            videoWaterMark.setFontShadow(videoWaterMark.getFontSize() / 10);
        } else {
            videoWaterMark.setFontShadow(0);
        }
        return videoWaterMark;
    }

}
