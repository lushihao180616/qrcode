package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.service.BusinessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("business")
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @RequestMapping("create")
    @ResponseBody
    public Result create(@RequestBody Map<String, Object> reqMap) {
        String logoSrc = (String) reqMap.get("logoSrc");
        return businessService.create(LSHMapUtils.mapToEntity(reqMap, Business.class), logoSrc);
    }

    @RequestMapping("update")
    @ResponseBody
    public Result update(@RequestBody Map<String, Object> reqMap) {
        String logoSrc = (String) reqMap.get("logoSrc");
        return businessService.update(LSHMapUtils.mapToEntity(reqMap, Business.class), logoSrc);
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(@RequestBody Map<String, Object> reqMap) {
        String code = (String) reqMap.get("code");
        return businessService.delete(code);
    }

    @RequestMapping("filter")
    @ResponseBody
    public Result filter(@RequestBody Map<String, Object> reqMap) {
        Business business = LSHMapUtils.mapToEntity(reqMap, Business.class);
        if ("".equals(business.getCode())) {
            business.setCode(null);
        }
        if ("".equals(business.getName())) {
            business.setName(null);
        }
        if ("".equals(business.getAddress())) {
            business.setAddress(null);
        }
        if ("".equals(business.getPhone())) {
            business.setPhone(null);
        }
        if ("".equals(business.getBusinessName())) {
            business.setBusinessName(null);
        }
        return new Result(true, businessService.filter(business), "搜索完成", null);
    }

}
