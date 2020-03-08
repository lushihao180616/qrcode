package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import com.lushihao.qrcode.util.LSHFtpUtil;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private LSHMACUtil lshmacUtil;
    @Resource
    private LSHFtpUtil lshFtpUtil;

    @RequestMapping("filter")
    @ResponseBody
    public Result filter() {
        if (!lshmacUtil.check()) {
            return null;
        }
        return new Result(true, userInfoService.filter(), "搜索完成", null);
    }

    @RequestMapping("test")
    @ResponseBody
    public void test() {
        System.out.println(lshFtpUtil.connectServer());
    }

}
