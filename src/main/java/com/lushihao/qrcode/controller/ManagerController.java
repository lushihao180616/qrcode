package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.service.manage.ManagerService;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("manager")
public class ManagerController {

    @Resource
    private ManagerService managerService;
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("create")
    @ResponseBody
    public Result create(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        return managerService.create(LSHMapUtils.mapToEntity(reqMap, Manager.class));
    }

    @RequestMapping("update")
    @ResponseBody
    public Result update(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        return managerService.update(LSHMapUtils.mapToEntity(reqMap, Manager.class));
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        String code = (String) reqMap.get("code");
        return managerService.delete(code);
    }

    @RequestMapping("filter")
    @ResponseBody
    public Result filter(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        Manager manager = LSHMapUtils.mapToEntity(reqMap, Manager.class);
        if ("".equals(manager.getCode())) {
            manager.setCode(null);
        }
        if ("".equals(manager.getName())) {
            manager.setName(null);
        }
        if ("".equals(manager.getPhone())) {
            manager.setPhone(null);
        }
        if ("".equals(manager.getIdCard())) {
            manager.setIdCard(null);
        }
        return new Result(true, managerService.filter(manager), "搜索完成", null);
    }

}
