package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserInfoService userInfoService;

    @RequestMapping("filter")
    @ResponseBody
    public Result filter() {
        UserInfo userInfo = userInfoService.filter();
        Result result = new Result(true, userInfo, null, null);
        return result;
    }

}
