package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("check")
public class CheckController {

    @RequestMapping("check")
    @ResponseBody
    public String check() {
        if (!LSHMACUtil.getLocalMac().equals("98-3B-8F-BD-BD-7A")) {
            return "0";
        }
        return "1";
    }

}
