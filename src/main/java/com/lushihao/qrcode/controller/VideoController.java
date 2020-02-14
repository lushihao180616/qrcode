package com.lushihao.qrcode.controller;

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

        return videoService.create();
    }

}
