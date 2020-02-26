package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.entity.video.VideoWaterMark;
import com.lushihao.qrcode.service.VideoService;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    private static boolean isChineseChar(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }

}
