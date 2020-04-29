package com.lushihao.qrcode.service.video.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.dao.ManagerMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.entity.video.VideoWaterMark;
import com.lushihao.qrcode.config.yml.ProjectBasicInfo;
import com.lushihao.qrcode.config.yml.UserBasicInfo;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import com.lushihao.qrcode.service.video.VideoWaterMarkService;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoWaterMarkServiceImpl implements VideoWaterMarkService {

    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private ManagerMapper managerMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;
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
    public Result addWaterMark(VideoWaterMark videoWaterMark) {
        String[] lines = new String[3];
        //获取商家
        Business business = new Business();
        business.setCode(videoWaterMark.getBusinessCode());
        List<Business> list = businessMapper.filter(business);
        if (list.size() == 1) {
            business = list.get(0);
            lines[0] = "名称：『" + business.getName() + "』";
            lines[1] = "电话：『" + business.getPhone() + "』";
            lines[2] = "地址：『" + business.getAddress() + "』";
        } else {
            return new Result(false, null, null, "商家不存在");
        }

        if (lshFfmpegUtil.checkFileType(videoWaterMark.getPath()) != lshFfmpegUtil.VIDEO) {
            return new Result(false, null, null, "不支持此文件格式");
        }
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoWaterMark.getPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        videoWaterMark.setNewPath(videoWaterMark.getPath().substring(0, videoWaterMark.getPath().lastIndexOf(".")) + "_waterMark.mp4");
        //需要画水印的图片
        BufferedImage bg = new BufferedImage(videoInfo.getWidth(), videoInfo.getHeight(), BufferedImage.TYPE_INT_RGB);
        int width = bg.getWidth();
        int height = bg.getHeight();
        int waterMarkHeight = (int) ((float) height * videoWaterMark.getHeight() / (float) 100);
        int fontSize = (int) (waterMarkHeight * 0.2);
        int offSet = (int) (waterMarkHeight * 0.05);
        Graphics2D bgG2 = bg.createGraphics();
        bg = bgG2.getDeviceConfiguration().createCompatibleImage(videoInfo.getWidth(), videoInfo.getHeight(), Transparency.TRANSLUCENT);
        bgG2 = bg.createGraphics();
        bgG2.setColor(getColor(videoWaterMark.getFontColor()));
        Font font = new Font("宋体", Font.PLAIN, fontSize);
        bgG2.setFont(font);
        bgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = bgG2.getFontMetrics(font);
        int waterMarkWidth = getWidth(lines, fm) + waterMarkHeight;
        int xWidth = (int) ((float) (width - waterMarkWidth) * videoWaterMark.getX() / (float) 100);
        int yHeight = (int) ((float) (height - waterMarkHeight) * videoWaterMark.getY() / (float) 100);
        bgG2.drawString(lines[0], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 1.5));
        bgG2.drawString(lines[1], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 3.0));
        bgG2.drawString(lines[2], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 4.5));

        BufferedImage logoImage = lshImageUtil.getImage(projectBasicInfo.getBusinessUrl() + "\\" + videoWaterMark.getBusinessCode() + "\\logo.png");
        if (logoImage == null) {
            return new Result(false, null, null, "商标不存在");
        }
        logoImage = roundImage(logoImage, logoImage.getWidth(), logoImage.getWidth());
        bgG2.drawImage(logoImage, xWidth + offSet, yHeight + offSet, waterMarkHeight - offSet, waterMarkHeight - offSet, null);
        bgG2.dispose();

        //删除测试文件
        File testFile = new File(videoWaterMark.getPath().substring(0, videoWaterMark.getPath().lastIndexOf(".")) + "_test.mp4");
        if (testFile.exists()) {
            testFile.delete();
        }
        if (!userInfoService.countSub(initProject.beanCosts.stream().filter(s -> s.getType().equals("videowatermark")).collect(Collectors.toList()).get(0).getBean(), userBasicInfo.getCode())) {
            return new Result(false, null, null, "金豆不够用了");
        }
        //加水印图片
        String newImagePath = videoWaterMark.getPath().substring(0, videoWaterMark.getPath().lastIndexOf(".")) + "_watermark.png";
        videoWaterMark.setImagePath(newImagePath);
        if (!lshImageUtil.sendImage(newImagePath, bg, "png")) {
            userInfoService.countAdd(initProject.beanCosts.stream().filter(s -> s.getType().equals("videowatermark")).collect(Collectors.toList()).get(0).getBean(), userBasicInfo.getCode());
            return new Result(false, null, null, "输出图片失败");
        }
        if (!lshFfmpegUtil.videoWaterMark(videoWaterMark)) {
            userInfoService.countAdd(initProject.beanCosts.stream().filter(s -> s.getType().equals("videowatermark")).collect(Collectors.toList()).get(0).getBean(), userBasicInfo.getCode());
            return new Result(false, null, null, "输出视频失败");
        }
        File nowFontImage = new File(videoWaterMark.getImagePath());
        if (nowFontImage.exists()) {
            nowFontImage.delete();
        }
        return new Result(true, videoWaterMark.getNewPath(), "添加成功", null);
    }

    @Override
    public Result testWaterMark(VideoWaterMark videoWaterMark) {
        String[] lines = new String[3];
        //获取商家
        Manager manager = new Manager();
        manager.setCode(videoWaterMark.getBusinessCode());
        List<Manager> list = managerMapper.filter(manager);
        if (list.size() == 1) {
            manager = list.get(0);
            lines[0] = "名称：『" + manager.getName() + "』";
            lines[1] = "电话：『" + manager.getPhone() + "』";
            lines[2] = "地址：『" + manager.getAddress() + "』";
        } else {
            return new Result(false, null, null, "管理员不存在");
        }

        if (lshFfmpegUtil.checkFileType(videoWaterMark.getPath()) != lshFfmpegUtil.VIDEO) {
            return new Result(false, null, null, "不支持此文件格式");
        }
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoWaterMark.getPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        videoWaterMark.setNewPath(videoWaterMark.getPath().substring(0, videoWaterMark.getPath().lastIndexOf(".")) + "_test.mp4");
        //需要画水印的图片
        BufferedImage bg = new BufferedImage(videoInfo.getWidth(), videoInfo.getHeight(), BufferedImage.TYPE_INT_RGB);
        int width = bg.getWidth();
        int height = bg.getHeight();
        int waterMarkHeight = (int) ((float) height * videoWaterMark.getHeight() / (float) 100);
        int fontSize = (int) (waterMarkHeight * 0.2);
        int offSet = (int) (waterMarkHeight * 0.05);
        Graphics2D bgG2 = bg.createGraphics();
        bg = bgG2.getDeviceConfiguration().createCompatibleImage(videoInfo.getWidth(), videoInfo.getHeight(), Transparency.TRANSLUCENT);
        bgG2 = bg.createGraphics();
        bgG2.setColor(getColor(videoWaterMark.getFontColor()));
        Font font = new Font("宋体", Font.PLAIN, fontSize);
        bgG2.setFont(font);
        bgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = bgG2.getFontMetrics(font);
        int waterMarkWidth = getWidth(lines, fm) + waterMarkHeight;
        int xWidth = (int) ((float) (width - waterMarkWidth) * videoWaterMark.getX() / (float) 100);
        int yHeight = (int) ((float) (height - waterMarkHeight) * videoWaterMark.getY() / (float) 100);
        bgG2.drawString(lines[0], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 1.5));
        bgG2.drawString(lines[1], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 3.0));
        bgG2.drawString(lines[2], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 4.5));

        BufferedImage logoImage = lshImageUtil.getImage(projectBasicInfo.getBusinessUrl() + "\\" + videoWaterMark.getManagerCode() + "\\logo.png");
        if (logoImage == null) {
            return new Result(false, null, null, "商标不存在");
        }
        logoImage = roundImage(logoImage, logoImage.getWidth(), logoImage.getWidth());
        bgG2.drawImage(logoImage, xWidth + offSet, yHeight + offSet, waterMarkHeight - offSet, waterMarkHeight - offSet, null);
        bgG2.dispose();

        //加水印图片
        String newImagePath = videoWaterMark.getPath().substring(0, videoWaterMark.getPath().lastIndexOf(".")) + "_watermark.png";
        videoWaterMark.setImagePath(newImagePath);
        if (!lshImageUtil.sendImage(newImagePath, bg, "png")) {
            return new Result(false, null, null, "输出图片失败");
        }
        if (!lshFfmpegUtil.videoWaterMark(videoWaterMark)) {
            return new Result(false, null, null, "输出视频失败");
        }
        File nowFontImage = new File(videoWaterMark.getImagePath());
        if (nowFontImage.exists()) {
            nowFontImage.delete();
        }
        return new Result(true, videoWaterMark.getNewPath(), "添加成功", null);
    }

    private Color getColor(String color) {
        if (color.equals("black")) {
            return Color.black;
        } else if (color.equals("white")) {
            return Color.white;
        } else if (color.equals("blue")) {
            return Color.blue;
        } else if (color.equals("green")) {
            return Color.green;
        } else if (color.equals("red")) {
            return Color.red;
        } else if (color.equals("yellow")) {
            return Color.yellow;
        } else if (color.equals("pink")) {
            return Color.pink;
        } else if (color.equals("cyan")) {
            return Color.cyan;
        } else if (color.equals("gray")) {
            return Color.gray;
        } else if (color.equals("orange")) {
            return Color.orange;
        }
        return null;
    }

    private int getWidth(String[] lines, FontMetrics fm) {
        int lineMaxWidth = 0;
        for (String line : lines) {
            if (fm.stringWidth(line) > lineMaxWidth) {
                lineMaxWidth = fm.stringWidth(line);
            }
        }
        return lineMaxWidth;
    }

    private BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius) {
        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, targetSize, targetSize, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return outputImage;
    }

}
