package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.qrcode.AllQRCodeTemple;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("qrTemple")
public class QRTempleController {

    @Resource
    private AllQRCodeTemple allQRCodeTemple;

    @RequestMapping("getAll")
    public void create() {
        allQRCodeTemple.getTempleList();
    }

}
