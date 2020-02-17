package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import com.lushihao.qrcode.entity.video.Video;
import com.lushihao.qrcode.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("video")
public class VideoController {

    @Resource
    private VideoService videoService;

    @RequestMapping("create")
    @ResponseBody
    public String create() {
        Video video = new Video();
        video.setBusinessCode("00000000");
        video.setOldVideoPath("");
        video.setNewVideoPath("");
        return videoService.create(video);
    }

}
