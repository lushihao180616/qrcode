package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.AllBusiness;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("business")
public class BusinessController {

    @Resource
    private AllBusiness allBusiness;

    @RequestMapping("getAll")
    public void create() {
        allBusiness.getInfoList();
    }

}
