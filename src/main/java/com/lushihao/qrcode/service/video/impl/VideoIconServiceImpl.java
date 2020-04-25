package com.lushihao.qrcode.service.video.impl;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoIcon;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.entity.yml.UserBasicInfo;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import com.lushihao.qrcode.service.video.VideoIconService;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class VideoIconServiceImpl implements VideoIconService {

    @Resource
    private LSHImageUtil lshImageUtil;
    @Resource
    private LSHFfmpegUtil lshFfmpegUtil;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserBasicInfo userBasicInfo;
    @Resource
    private InitProject initProject;

    @Override
    public Result addIcon(VideoIcon videoIcon) {
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoIcon.getPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        if (lshFfmpegUtil.checkFileType(videoIcon.getPath()) != lshFfmpegUtil.VIDEO) {
            return new Result(false, null, null, "不支持此文件格式");
        }
        BufferedImage bg = new BufferedImage(videoInfo.getWidth(), videoInfo.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }
        BufferedImage icon = lshImageUtil.getImage(videoIcon.getIcon());
        if (icon == null) {
            return new Result(false, null, null, "添加图标不存在");
        }
        videoIcon.setNewPath(videoIcon.getPath().substring(0, videoIcon.getPath().lastIndexOf(".")) + "_icon.mp4");
        Graphics2D bgG2 = bg.createGraphics();
        bg = bgG2.getDeviceConfiguration().createCompatibleImage(videoInfo.getWidth(), videoInfo.getHeight(), Transparency.TRANSLUCENT);
        bgG2 = bg.createGraphics();
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();
        int iconWidth = (int) ((float) bgWidth * videoIcon.getWidth() / (float) 100);
        int iconHeight = (int) ((float) bgHeight * videoIcon.getHeight() / (float) 100);
        int xWidth = (int) ((float) (bgWidth - iconWidth) * videoIcon.getX() / (float) 100);
        int yHeight = (int) ((float) (bgHeight - iconHeight) * videoIcon.getY() / (float) 100);
        bgG2.drawImage(icon, xWidth, yHeight, iconWidth, iconHeight, null);
        bgG2.dispose();
        //删除测试文件
        File testFile = new File(videoIcon.getPath().substring(0, videoIcon.getPath().lastIndexOf(".")) + "_test.mp4");
        if (testFile.exists()) {
            testFile.delete();
        }
        if (!userInfoService.countSub(initProject.beanCosts.stream().filter(s -> s.getType().equals("videoicon")).collect(Collectors.toList()).get(0).getBean(), userBasicInfo.getCode())) {
            return new Result(false, null, null, "金豆不够用了");
        }
        //加水印图片
        String newImagePath = videoIcon.getPath().substring(0, videoIcon.getPath().lastIndexOf(".")) + "_icon.jpg";
        videoIcon.setImagePath(newImagePath);
        if (!lshImageUtil.sendImage(newImagePath, bg, "png")) {
            userInfoService.countAdd(initProject.beanCosts.stream().filter(s -> s.getType().equals("videoicon")).collect(Collectors.toList()).get(0).getBean(), userBasicInfo.getCode());
            return new Result(false, null, null, "输出图片失败");
        }
        if (!lshFfmpegUtil.videoIcon(videoIcon)) {
            userInfoService.countAdd(initProject.beanCosts.stream().filter(s -> s.getType().equals("videoicon")).collect(Collectors.toList()).get(0).getBean(), userBasicInfo.getCode());
            return new Result(false, null, null, "输出视频失败");
        }
        File nowFontImage = new File(videoIcon.getImagePath());
        if (nowFontImage.exists()) {
            nowFontImage.delete();
        }
        return new Result(true, videoIcon.getNewPath(), "添加成功", null);
    }

    @Override
    public Result testIcon(VideoIcon videoIcon) {
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoIcon.getPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        if (lshFfmpegUtil.checkFileType(videoIcon.getPath()) != lshFfmpegUtil.VIDEO) {
            return new Result(false, null, null, "不支持此文件格式");
        }
        BufferedImage bg = new BufferedImage(videoInfo.getWidth(), videoInfo.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }
        BufferedImage icon = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D iconG2 = icon.createGraphics();
        iconG2.setBackground(Color.WHITE);
        iconG2.clearRect(0, 0, 100, 100);
        iconG2.dispose();
        if (icon == null) {
            return new Result(false, null, null, "添加图标不存在");
        }
        videoIcon.setNewPath(videoIcon.getPath().substring(0, videoIcon.getPath().lastIndexOf(".")) + "_test.mp4");
        Graphics2D bgG2 = bg.createGraphics();
        bg = bgG2.getDeviceConfiguration().createCompatibleImage(videoInfo.getWidth(), videoInfo.getHeight(), Transparency.TRANSLUCENT);
        bgG2 = bg.createGraphics();
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();
        int iconWidth = (int) ((float) bgWidth * videoIcon.getWidth() / (float) 100);
        int iconHeight = (int) ((float) bgHeight * videoIcon.getHeight() / (float) 100);
        int xWidth = (int) ((float) (bgWidth - iconWidth) * videoIcon.getX() / (float) 100);
        int yHeight = (int) ((float) (bgHeight - iconHeight) * videoIcon.getY() / (float) 100);
        bgG2.drawImage(icon, xWidth, yHeight, iconWidth, iconHeight, null);
        bgG2.dispose();
        //加水印图片
        String newImagePath = videoIcon.getPath().substring(0, videoIcon.getPath().lastIndexOf(".")) + "_icon.jpg";
        videoIcon.setImagePath(newImagePath);
        if (!lshImageUtil.sendImage(newImagePath, bg, "png")) {
            return new Result(false, null, null, "输出图片失败");
        }
        if (!lshFfmpegUtil.videoIcon(videoIcon)) {
            return new Result(false, null, null, "输出视频失败");
        }
        File nowFontImage = new File(videoIcon.getImagePath());
        if (nowFontImage.exists()) {
            nowFontImage.delete();
        }
        return new Result(true, videoIcon.getNewPath(), "添加成功", null);
    }

}
