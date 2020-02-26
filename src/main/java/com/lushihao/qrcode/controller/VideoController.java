package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.video.VideoWaterMark;
import com.lushihao.qrcode.service.VideoService;
import com.lushihao.qrcode.util.LSHMACUtil;
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
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("add")
    @ResponseBody
    public String create(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        VideoWaterMark videoWaterMark = new VideoWaterMark();
        String code = (String) reqMap.get("businessCode");
        videoWaterMark.setOldVideoPath((String) reqMap.get("path"));
        videoWaterMark.setFontShadow(videoWaterMark.getFontSize() / 9);
        return videoService.create(videoWaterMark, code);
    }

}
