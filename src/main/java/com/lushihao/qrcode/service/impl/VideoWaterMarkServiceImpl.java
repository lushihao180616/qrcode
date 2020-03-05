package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.dao.ManagerMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.entity.video.VideoWaterMark;
import com.lushihao.qrcode.service.VideoWaterMarkService;
import com.lushihao.qrcode.util.LSHCharUtil;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service
public class VideoWaterMarkServiceImpl implements VideoWaterMarkService {

    @Resource
    private LSHFfmpegUtil lshFfmpegUtil;
    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private ManagerMapper managerMapper;
    @Resource
    private LSHCharUtil lshCharUtil;

    @Override
    public Result create(VideoWaterMark videoWaterMark, String code) {
        //获得商家
        Business business = new Business();
        business.setCode(code);
        List<Business> list = businessMapper.filter(business);
        if (list.size() > 0) {
            videoWaterMark.setBusiness(list.get(0));
        } else {
            return new Result(false, null, null, "商家不存在");
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
            return new Result(false, null, null, "不支持此文件格式");
        }
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoWaterMark.getOldVideoPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        String name = videoWaterMark.getBusiness().getName();
        int num = 0;
        for (char c : name.toCharArray()) {
            if (lshCharUtil.isChineseChar(c)) {
                num += 2;
            } else {
                num += 1;
            }
        }
        int fontWidth = (int) (num * ((float) videoWaterMark.getFontSize() / 2));
        videoWaterMark.setFontX((int) ((videoInfo.getWidth() - fontWidth) * ((float) videoWaterMark.getFontX() / 100)));
        videoWaterMark.setFontY((int) ((videoInfo.getHeight() - videoWaterMark.getFontSize()) * ((float) videoWaterMark.getFontY() / 100)));
        //执行加水印
        if (!lshFfmpegUtil.videoWaterMark(videoWaterMark)) {
            return new Result(false, null, null, "添加失败，请重启软件后再试");
        } else {
            return new Result(true, videoWaterMark.getNewVideoPath(), "添加成功", null);
        }
    }

    public Result test(VideoWaterMark videoWaterMark, String code) {
        //获得商家
        Manager manager = new Manager();
        manager.setCode(code);
        List<Manager> list = managerMapper.filter(manager);
        if (list.size() > 0) {
            videoWaterMark.setManager(list.get(0));
        } else {
            return new Result(false, null, null, "商家不存在");
        }

        //获得输出文件地址
        String testVideoPath = videoWaterMark.getOldVideoPath().substring(0, videoWaterMark.getOldVideoPath().lastIndexOf(".")) + "_test.mp4";
        videoWaterMark.setNewVideoPath(testVideoPath);
        if (lshFfmpegUtil.checkFileType(testVideoPath) != lshFfmpegUtil.VIDEO) {
            return new Result(false, null, null, "不支持此文件格式");
        }
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoWaterMark.getOldVideoPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        String name = videoWaterMark.getManager().getName();
        int num = 0;
        for (char c : name.toCharArray()) {
            if (lshCharUtil.isChineseChar(c)) {
                num += 2;
            } else {
                num += 1;
            }
        }
        int fontWidth = (int) (num * ((float) videoWaterMark.getFontSize() / 2));
        videoWaterMark.setFontX((int) ((videoInfo.getWidth() - fontWidth) * ((float) videoWaterMark.getFontX() / 100)));
        videoWaterMark.setFontY((int) ((videoInfo.getHeight() - videoWaterMark.getFontSize()) * ((float) videoWaterMark.getFontY() / 100)));
        //执行加水印
        if (!lshFfmpegUtil.videoWaterMark(videoWaterMark)) {
            return new Result(false, null, null, "添加失败，请重启软件后再试");
        } else {
            return new Result(true, videoWaterMark.getNewVideoPath(), "添加成功", null);
        }
    }

}
