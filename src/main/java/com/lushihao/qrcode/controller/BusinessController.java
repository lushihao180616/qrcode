package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.service.BusinessService;
import com.lushihao.qrcode.util.LSHMACUtil;
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
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("create")
    @ResponseBody
    public Result create(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        String logoSrc = (String) reqMap.get("logoSrc");
        Business business = LSHMapUtils.mapToEntity(reqMap, Business.class);
        Result result = new Result(true, null, businessService.create(business, logoSrc), null);
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    public Result update(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        String logoSrc = (String) reqMap.get("logoSrc");
        Business business = LSHMapUtils.mapToEntity(reqMap, Business.class);
        Result result = new Result(true, null, businessService.update(business, logoSrc), null);
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        String code = (String) reqMap.get("code");
        Result result = new Result(true, null, businessService.delete(code), null);
        return result;
    }

    @RequestMapping("filter")
    @ResponseBody
    public Result filter(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
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
        Result result = new Result(true, businessService.filter(business), null, null);
        return result;
    }

}
