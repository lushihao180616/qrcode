package com.lushihao.qrcode.controller;

import com.alibaba.druid.util.StringUtils;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
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
    @Resource
    private InitProject initProject;

    @RequestMapping("filter")
    @ResponseBody
    public Result filter() {
        if (!lshmacUtil.check()) {
            return null;
        }
        initProject.getUserInfo();
        return new Result(true, userInfoService.filter(), "搜索完成", null);
    }

    @RequestMapping("modify")
    @ResponseBody
    public Result modify(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        String logoPath = null;
        if (reqMap.get("logo") != null && !"".equals(reqMap.get("logo"))) {
            logoPath = (String) reqMap.get("logo");
        }
        if (StringUtils.equals((String) reqMap.get("type"), "1")) {//商家
            Business business = new Business();
            business.setCode((String) reqMap.get("code"));
            business.setName((String) reqMap.get("name"));
            business.setBusinessName((String) reqMap.get("businessName"));
            business.setPhone((String) reqMap.get("phone"));
            business.setAddress((String) reqMap.get("address"));
            return userInfoService.modify(business, logoPath);
        } else {
            Manager manager = new Manager();
            manager.setCode((String) reqMap.get("code"));
            manager.setName((String) reqMap.get("businessName"));
            manager.setPhone((String) reqMap.get("phone"));
            manager.setAddress((String) reqMap.get("address"));
            return userInfoService.modify(manager, logoPath);
        }
    }

}
