package com.lushihao.qrcode.controller;

import com.lushihao.myutils.json.LSHJsonUtils;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.service.UserInfoService;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody Map<String, Object> reqMap) {
//        if (!lshmacUtil.check()) {
//            return null;
//        }
        return userInfoService.create(new UserInfo());
    }

    @RequestMapping("filter")
    @ResponseBody
    public Result filter() {
//        if (!lshmacUtil.check()) {
//            return null;
//        }
        UserInfo userInfo = userInfoService.filter();
        Result result = new Result(true, userInfo, null, null);
        return result;
    }

}
