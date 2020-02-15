package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.image.WaterMark;
import com.lushihao.qrcode.service.ImageService;
import com.lushihao.qrcode.service.impl.ImageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("waterMark")
public class ImageAddController {

    @Resource
    private ImageService imageService;

    @RequestMapping("add")
    @ResponseBody
    public String addWaterMark() {
        WaterMark wm = new WaterMark();
        wm.setBusinessCode("918d639a");
        wm.setPath("C:\\Users\\86153\\Desktop\\test\\bj.jpg");
        wm.setIfShowLogo(true);
        wm.setIfShowFont(true);
        wm.setWidth(100);
        wm.setHeight(100);
        wm.setX(50);
        wm.setY(50);
        return imageService.addWaterMark(wm);
    }

}
