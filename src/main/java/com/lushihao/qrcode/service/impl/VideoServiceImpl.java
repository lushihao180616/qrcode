package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.entity.video.VideoWaterMark;
import com.lushihao.qrcode.service.VideoService;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Resource
    private LSHFfmpegUtil lshFfmpegUtil;
    @Resource
    private BusinessMapper businessMapper;

    @Override
    public String create(VideoWaterMark videoWaterMark, String code) {
        //获得商家
        Business business = new Business();
        business.setCode(code);
        List<Business> list = businessMapper.filter(business);
        if (list.size() > 0) {
            videoWaterMark.setBusiness(list.get(0));
        } else {
            return "商家不存在";
        }
        //删除测试文件
        File testFile = new File(videoWaterMark.getOldVideoPath().substring(0, videoWaterMark.getOldVideoPath().lastIndexOf(".")) + "_test.mp4");
        if (testFile.exists()) {
            testFile.delete();
        }
        //获得输出文件地址
        String newVideoPath = videoWaterMark.getOldVideoPath().substring(0, videoWaterMark.getOldVideoPath().lastIndexOf(".")) + "_new.mp4";
        videoWaterMark.setNewVideoPath(newVideoPath);
        if (lshFfmpegUtil.checkFileType(newVideoPath) != lshFfmpegUtil.VIDEO) {
            return "当前文件格式不支持";
        }
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoWaterMark.getOldVideoPath());
        String name = videoWaterMark.getBusiness().getName();
        int num = 0;
        for (char c : name.toCharArray()) {
            if (isChineseChar(c)) {
                num += 2;
            } else {
                num += 1;
            }
        }
        int fontWidth = (int) (num * ((float) videoWaterMark.getFontSize() / 2));
        videoWaterMark.setFontX((int) ((videoInfo.getWidth() - fontWidth) * ((float) videoWaterMark.getFontX() / 100)));
        videoWaterMark.setFontY((int) ((videoInfo.getHeight() - videoWaterMark.getFontSize()) * ((float) videoWaterMark.getFontY() / 100)));
        //执行加水印
        return lshFfmpegUtil.videoWaterMark(videoWaterMark);
    }

    public String test(VideoWaterMark videoWaterMark, String code) {
        //获得商家
        Business business = new Business();
        business.setCode(code);
        List<Business> list = businessMapper.filter(business);
        if (list.size() > 0) {
            videoWaterMark.setBusiness(list.get(0));
        } else {
            return "商家不存在";
        }
        //获得输出文件地址
        String testVideoPath = videoWaterMark.getOldVideoPath().substring(0, videoWaterMark.getOldVideoPath().lastIndexOf(".")) + "_test.mp4";
        videoWaterMark.setNewVideoPath(testVideoPath);
        if (lshFfmpegUtil.checkFileType(testVideoPath) != lshFfmpegUtil.VIDEO) {
            return "当前文件格式不支持";
        }
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoWaterMark.getOldVideoPath());
        String name = videoWaterMark.getBusiness().getName();
        int num = 0;
        for (char c : name.toCharArray()) {
            if (isChineseChar(c)) {
                num += 2;
            } else {
                num += 1;
            }
        }
        int chineseNum = num / 2;
        String businessName = "";
        for (int i = 0; i < chineseNum; i++) {
            if (i % 6 == 0) {
                businessName += "超";
            }
            if (i % 6 == 1) {
                businessName += "级";
            }
            if (i % 6 == 2) {
                businessName += "码";
            }
            if (i % 6 == 3) {
                businessName += "丽";
            }
            if (i % 6 == 4) {
                businessName += "CJ";
            }
            if (i % 6 == 5) {
                businessName += "ML";
            }
        }
        if (num % 2 > 0) {
            businessName += "0";
        }
        business.setName(businessName);
        videoWaterMark.setBusiness(business);
        int fontWidth = (int) (num * ((float) videoWaterMark.getFontSize() / 2));
        videoWaterMark.setFontX((int) ((videoInfo.getWidth() - fontWidth) * ((float) videoWaterMark.getFontX() / 100)));
        videoWaterMark.setFontY((int) ((videoInfo.getHeight() - videoWaterMark.getFontSize()) * ((float) videoWaterMark.getFontY() / 100)));
        //执行加水印
        return lshFfmpegUtil.videoWaterMark(videoWaterMark);
    }

    private static boolean isChineseChar(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }

}
