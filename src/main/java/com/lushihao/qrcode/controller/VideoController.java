package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoCut;
import com.lushihao.qrcode.entity.video.VideoWaterMark;
import com.lushihao.qrcode.service.VideoCutService;
import com.lushihao.qrcode.service.VideoWaterMarkService;
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
    private VideoCutService videoCutService;
    @Resource
    private VideoWaterMarkService videoWaterMarkService;
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("addCut")
    @ResponseBody
    public Result addCut(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        VideoCut videoCut = transformCut(reqMap);
        return videoCutService.addCut(videoCut);
    }

    private VideoCut transformCut(Map<String, Object> reqMap) {
        VideoCut videoCut = new VideoCut();
        videoCut.setPath((String) reqMap.get("path"));
        videoCut.setStart((Integer) reqMap.get("start"));
        videoCut.setEnd((Integer) reqMap.get("end"));
        return videoCut;
    }

    @RequestMapping("addWaterMark")
    @ResponseBody
    public Result addWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        VideoWaterMark videoWaterMark = transformWaterMark(reqMap);
        String code = (String) reqMap.get("businessCode");
        return videoWaterMarkService.addWaterMark(videoWaterMark, code);
    }

    @RequestMapping("testWaterMark")
    @ResponseBody
    public Result testWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        VideoWaterMark videoWaterMark = transformWaterMark(reqMap);
        String code = "00000000";
        return videoWaterMarkService.testWaterMark(videoWaterMark, code);
    }

    private VideoWaterMark transformWaterMark(Map<String, Object> reqMap) {
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
