package com.lushihao.qrcode.controller;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageCut;
import com.lushihao.qrcode.entity.image.ImageFont;
import com.lushihao.qrcode.entity.image.ImageWaterMark;
import com.lushihao.qrcode.service.ImageCutService;
import com.lushihao.qrcode.service.ImageFontService;
import com.lushihao.qrcode.service.ImageWaterMarkService;
import com.lushihao.qrcode.util.LSHMACUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("image")
public class ImageController {

    @Resource
    private ImageWaterMarkService imageWaterMarkService;
    @Resource
    private ImageCutService imageCutService;
    @Resource
    private ImageFontService imageFontService;
    @Resource
    private LSHMACUtil lshmacUtil;

    @RequestMapping("addWaterMark")
    @ResponseBody
    public Result addWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        ImageWaterMark wm = transformWaterMark(reqMap);
        wm.setBusinessCode((String) reqMap.get("businessCode"));
        return imageWaterMarkService.addWaterMark(wm);
    }

    @RequestMapping("testWaterMark")
    @ResponseBody
    public Result testWaterMark(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        ImageWaterMark wm = transformWaterMark(reqMap);
        wm.setManagerCode("00000000");
        return imageWaterMarkService.testWaterMark(wm);
    }

    private ImageWaterMark transformWaterMark(Map<String, Object> reqMap) {
        ImageWaterMark wm = new ImageWaterMark();
        wm.setPath((String) reqMap.get("path"));
        wm.setHeightPercentage((Integer) reqMap.get("height"));
        wm.setxPercentage((Integer) reqMap.get("x"));
        wm.setyPercentage((Integer) reqMap.get("y"));
        wm.setAlpha((Integer) reqMap.get("alpha"));
        return wm;
    }

    @RequestMapping("addCut")
    @ResponseBody
    public Result addCut(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        ImageCut cut = transformCut(reqMap);
        return imageCutService.addCut(cut);
    }

    @RequestMapping("testCut")
    @ResponseBody
    public Result testCut(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        ImageCut cut = transformCut(reqMap);
        return imageCutService.testCut(cut);
    }

    private ImageCut transformCut(Map<String, Object> reqMap) {
        ImageCut cut = new ImageCut();
        cut.setWidth((Integer) reqMap.get("width"));
        cut.setHeight((Integer) reqMap.get("height"));
        cut.setX((Integer) reqMap.get("x"));
        cut.setY((Integer) reqMap.get("y"));
        cut.setPath((String) reqMap.get("path"));
        cut.setAlpha((Integer) reqMap.get("alpha"));
        return cut;
    }

    @RequestMapping("addFont")
    @ResponseBody
    public Result addFont(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        ImageFont font = transformFont(reqMap);
        return imageFontService.addFont(font);
    }

    @RequestMapping("testFont")
    @ResponseBody
    public Result testFont(@RequestBody Map<String, Object> reqMap) {
        if (!lshmacUtil.check()) {
            return null;
        }
        ImageFont font = transformFont(reqMap);
        return imageFontService.testFont(font);
    }

    private ImageFont transformFont(Map<String, Object> reqMap) {
        ImageFont font = new ImageFont();
        font.setMessage((String) reqMap.get("message"));
        font.setLayout((String) reqMap.get("layout"));
        font.setX((Integer) reqMap.get("x"));
        font.setY((Integer) reqMap.get("y"));
        font.setPath((String) reqMap.get("path"));
        font.setSize((Integer) reqMap.get("size"));
        font.setColor((String) reqMap.get("color"));
        return font;
    }

}
