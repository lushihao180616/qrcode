package com.lushihao.qrcode.controller;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.qrcode.QRCodeTemple;
import com.lushihao.qrcode.service.QRTempleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("temple")
public class QRTempleController {

    @Resource
    private QRTempleService qrTempleService;

    @RequestMapping("create")
    @ResponseBody
    public String create(@RequestBody Map<String, Object> reqMap) {
        String templeItemsSrc = (String) reqMap.get("templeItemsSrc");
        QRCodeTemple qrCodeTemple = LSHMapUtils.mapToEntity(reqMap, QRCodeTemple.class);
        String back = qrTempleService.create(qrCodeTemple, templeItemsSrc);
        return back;
    }

    @RequestMapping("filter")
    @ResponseBody
    public List<QRCodeTemple> filter(@RequestBody Map<String, Object> reqMap) {
        String code = (String) reqMap.get("code");
        if ("".equals(code)) {
            code = null;
        }
        return qrTempleService.filter(code);
    }

}
