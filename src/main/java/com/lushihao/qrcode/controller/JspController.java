package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.service.BusinessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("jsp")
public class JspController {
    @Resource
    private BusinessService businessService;

    @RequestMapping("getQRCode")
    public String getIndex(HttpServletRequest request) {
        return "qrcode";
    }

    @RequestMapping("getBusiness")
    public String getBusiness(HttpServletRequest request) {
        return "business";
    }

    @RequestMapping("getTemple")
    public String getTemple(HttpServletRequest request) {
        return "temple";
    }

}
