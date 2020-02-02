package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.service.BusinessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("business")
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody Map<String, Object> reqMap) {
        String logoSrc = (String) reqMap.get("logoSrc");
        Business business = LSHMapUtils.mapToEntity(reqMap, Business.class);
        String back = businessService.create(business, logoSrc);
        return back;
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody Map<String, Object> reqMap) {
        String logoSrc = (String) reqMap.get("logoSrc");
        Business business = LSHMapUtils.mapToEntity(reqMap, Business.class);
        String back = businessService.update(business, logoSrc);
        return back;
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(@RequestBody Map<String, Object> reqMap) {
        String code = (String) reqMap.get("code");
        String back = businessService.delete(code);
        return back;
    }

    @RequestMapping("filter")
    @ResponseBody
    public List<Business> filter(@RequestBody Map<String, Object> reqMap) {
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
        return businessService.filter(business);
    }

}
