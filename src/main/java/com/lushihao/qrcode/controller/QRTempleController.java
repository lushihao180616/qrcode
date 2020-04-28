package com.lushihao.qrcode.controller;

import com.alibaba.druid.util.StringUtils;
import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.temple.QRTempleService;
import com.lushihao.qrcode.util.LSHFtpUtil;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("temple")
public class QRTempleController {

    @Resource
    private QRTempleService qrTempleService;
    @Resource
    private LSHMACUtil lshmacUtil;
    @Resource
    private LSHFtpUtil lshFtpUtil;
    @Resource
    private InitProject initProject;
    @Resource
    private ProjectBasicInfo projectBasicInfo;

    @RequestMapping("create")
    @ResponseBody
    public Result create(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        return qrTempleService.create(transform(reqMap), (String) reqMap.get("templeItemsPath"));
    }

    @RequestMapping("update")
    @ResponseBody
    public Result update(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        return qrTempleService.update(transform(reqMap), (String) reqMap.get("templeItemsPath"));
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result delete(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        return qrTempleService.delete((String) reqMap.get("code"));
    }

    @RequestMapping("filter")
    @ResponseBody
    public Result filter(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        String code = (String) reqMap.get("code");
        if ("".equals(code)) {
            code = null;
        }
        return new Result(true, qrTempleService.filter(code), "搜索完成", null);
    }

    @RequestMapping("downLoad")
    @ResponseBody
    public Result downLoad(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        String downLoadTempleCode = (String) reqMap.get("downLoadTemple");
        List<QRCodeTemple> nowTemple = initProject.qrCodeTempleList.stream().filter(s -> StringUtils.equals(s.getCode(), downLoadTempleCode)).collect(Collectors.toList());
        if (nowTemple == null || nowTemple.size() == 0) {
            return new Result(false, null, null, "下载的模板不存在，请检查模板号是否正确");
        } else {
            if (nowTemple.get(0).getMoney() > 0) {
                return new Result(true, "下载此模板需要花费" + nowTemple.get(0).getMoney() + "金豆", null, null);
            }
        }
        if (initProject.bucketTemple == null) {
            return new Result(false, null, null, "网络连接失败");
        }
        if (lshFtpUtil.connectServer(initProject.bucketTemple.getIp(), Integer.valueOf(initProject.bucketTemple.getPort()), initProject.bucketTemple.getUserName(), initProject.bucketTemple.getPwd())) {
            File dir = new File(projectBasicInfo.getTempleUrl() + "\\" + downLoadTempleCode);
            dir.mkdir();
            if (lshFtpUtil.downloadDir(initProject.bucketTemple.getName() + "/" + downLoadTempleCode, "C:\\qrcode\\qrcodeTemple\\" + downLoadTempleCode)) {
                return new Result(true, null, "下载成功", null);
            } else {
                dir.delete();
                return new Result(false, null, null, "下载的模板不存在，请检查模板号是否正确");
            }
        } else {
            return new Result(false, null, null, "网络连接失败");
        }
    }

    /**
     * 前台转后台
     *
     * @param reqMap
     * @return
     */
    private QRCodeTemple transform(Map<String, Object> reqMap) {
        QRCodeTemple qrCodeTemple = LSHMapUtils.mapToEntity(reqMap, QRCodeTemple.class);
        String code = (String) reqMap.get("code");
        if (code.charAt(0) == 'J') {
            qrCodeTemple.setIfGif(false);
        } else if (code.charAt(0) == 'D') {
            qrCodeTemple.setIfGif(true);
        }
        if (code.charAt(1) == 'W') {
            qrCodeTemple.setIfShowLogo(false);
        } else if (code.charAt(1) == 'Y') {
            qrCodeTemple.setIfShowLogo(true);
        }
        if (code.charAt(2) == '0') {
            qrCodeTemple.setIfOnly(true);
        } else if (code.charAt(2) == '1') {
            qrCodeTemple.setIfOnly(false);
        }
        if (code.charAt(3) == '0') {
            qrCodeTemple.setArti("0-1-2-3-4");
        } else if (code.charAt(3) == '1') {
            qrCodeTemple.setArti("0-1-2-5-6");
        }
        if (code.charAt(2) == '1' && code.charAt(4) == '1') {
            qrCodeTemple.setIfSelfBg(true);
        } else {
            qrCodeTemple.setIfSelfBg(false);
        }
        qrCodeTemple.setMoney(Double.parseDouble(reqMap.get("money").toString()));
        if (reqMap.get("width") == null) {
            qrCodeTemple.setWidth(0);
        } else {
            qrCodeTemple.setWidth((Integer) reqMap.get("width"));
        }
        if (reqMap.get("height") == null) {
            qrCodeTemple.setHeight(0);
        } else {
            qrCodeTemple.setHeight((Integer) reqMap.get("height"));
        }
        if (reqMap.get("x") == null) {
            qrCodeTemple.setX(0);
        } else {
            qrCodeTemple.setX((Integer) reqMap.get("x"));
        }
        if (reqMap.get("y") == null) {
            qrCodeTemple.setY(0);
        } else {
            qrCodeTemple.setY((Integer) reqMap.get("y"));
        }
        qrCodeTemple.setIconNum((Integer) reqMap.get("iconNum"));
        if (reqMap.get("angle") == null || "".equals(reqMap.get("angle"))) {
            qrCodeTemple.setAngle(0);
        } else {
            qrCodeTemple.setAngle((Integer) reqMap.get("angle"));
        }
        if (reqMap.get("multiple") == null) {
            qrCodeTemple.setMultiple(0);
        } else {
            qrCodeTemple.setMultiple((Integer) reqMap.get("multiple"));
        }
        if (qrCodeTemple.isIfGif()) {
            qrCodeTemple.setIfGif(true);
            String frame = (String) reqMap.get("frame");
            if (frame == null || "".equals(frame)) {
                qrCodeTemple.setFrame(8);
                qrCodeTemple.setStartQRFrame(0);
                qrCodeTemple.setEndQRFrame(0);
            } else {
                String[] frames = frame.split("/");
                qrCodeTemple.setFrame(Integer.valueOf(frames[0]));
                if (frames.length > 1) {
                    qrCodeTemple.setStartQRFrame(Integer.valueOf(frames[1]));
                }
                if (frames.length > 2) {
                    qrCodeTemple.setEndQRFrame(Integer.valueOf(frames[2]));
                }
            }
        } else {
            qrCodeTemple.setIfGif(false);
            qrCodeTemple.setFrame(0);
            qrCodeTemple.setStartQRFrame(0);
            qrCodeTemple.setEndQRFrame(0);
        }
        return qrCodeTemple;
    }

}
