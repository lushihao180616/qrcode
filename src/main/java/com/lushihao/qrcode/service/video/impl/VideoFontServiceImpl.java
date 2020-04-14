package com.lushihao.qrcode.service.video.impl;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoFont;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.entity.yml.UserBasicInfo;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import com.lushihao.qrcode.service.video.VideoFontService;
import com.lushihao.qrcode.util.LSHCharUtil;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoFontServiceImpl implements VideoFontService {

    @Resource
    private LSHImageUtil lshImageUtil;
    @Resource
    private LSHCharUtil lshCharUtil;
    @Resource
    private LSHFfmpegUtil lshFfmpegUtil;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserBasicInfo userBasicInfo;
    @Resource
    private ProjectBasicInfo projectBasicInfo;

    @Override
    public Result addFont(VideoFont videoFont) {
        if (!lshCharUtil.isDefaultChar(videoFont.getMessage())) {
            return new Result(false, null, null, "只支持汉字、英文字母、数字");
        }
        if (lshFfmpegUtil.checkFileType(videoFont.getPath()) != lshFfmpegUtil.VIDEO) {
            return new Result(false, null, null, "不支持此文件格式");
        }
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoFont.getPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        videoFont.setNewPath(videoFont.getPath().substring(0, videoFont.getPath().lastIndexOf(".")) + "_font.mp4");
        String[] lines = videoFont.getMessage().split("\\n");
        BufferedImage fontImage = new BufferedImage(videoInfo.getWidth(), videoInfo.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bgG2 = fontImage.createGraphics();//设置透明  start
        fontImage = bgG2.getDeviceConfiguration().createCompatibleImage(videoInfo.getWidth(), videoInfo.getHeight(), Transparency.TRANSLUCENT);
        bgG2 = fontImage.createGraphics();
        bgG2.setColor(getColor(videoFont.getColor()));
        Font font = new Font("宋体", Font.PLAIN, videoFont.getSize());
        bgG2.setFont(font);
        bgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = bgG2.getFontMetrics(font);
        int massageWidth = getWidth(lines, fm);
        int massageHeight = getHeight(lines, videoFont.getSize());
        int xWidth = (int) ((float) (fontImage.getWidth() - massageWidth) * videoFont.getX() / (float) 100);
        int yHeight = (int) ((float) (fontImage.getHeight() - massageHeight) * videoFont.getY() / (float) 100 + videoFont.getSize() * 0.9);
        Map<Integer, ArrayList<Integer>> map = getInfo(lines, fm, videoFont.getLayout(), massageWidth, videoFont.getSize());
        for (int i = 0; i < lines.length; i++) {
            bgG2.drawString(lines[i], xWidth + map.get(i).get(0), yHeight + map.get(i).get(1));
        }
        // 释放对象
        bgG2.dispose();
        //删除测试文件
        File testFile = new File(videoFont.getPath().substring(0, videoFont.getPath().lastIndexOf(".")) + "_test.mp4");
        if (testFile.exists()) {
            testFile.delete();
        }
        if (!userInfoService.countSub(projectBasicInfo.getMediaBean(), userBasicInfo.getCode())) {
            return new Result(false, null, null, "金豆不够用了");
        }
        //加水印图片
        String newImagePath = videoFont.getPath().substring(0, videoFont.getPath().lastIndexOf(".")) + "_font.jpg";
        videoFont.setImagePath(newImagePath);
        if (!lshImageUtil.sendImage(newImagePath, fontImage, "png")) {
            userInfoService.countAdd(projectBasicInfo.getMediaBean(), userBasicInfo.getCode());
            return new Result(false, null, null, "输出视频失败");
        }
        if (!lshFfmpegUtil.videoFont(videoFont)) {
            userInfoService.countAdd(projectBasicInfo.getMediaBean(), userBasicInfo.getCode());
            return new Result(false, null, null, "输出视频失败");
        }
        File nowFontImage = new File(videoFont.getImagePath());
        if (nowFontImage.exists()) {
            nowFontImage.delete();
        }
        return new Result(true, videoFont.getNewPath(), "添加成功", null);
    }

    @Override
    public Result testFont(VideoFont videoFont) {
        if (!lshCharUtil.isDefaultChar(videoFont.getMessage())) {
            return new Result(false, null, null, "只支持汉字、英文字母、数字");
        }
        videoFont.setNewPath(videoFont.getPath().substring(0, videoFont.getPath().lastIndexOf(".")) + "_test.mp4");
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoFont.getPath());
        String[] lines = videoFont.getMessage().split("\\n");
        int index = 0;
        for (int i = 0; i < lines.length; i++) {
            String str = lines[i];
            int num = 0;
            char[] chars = str.toCharArray();
            for (char charItem : chars) {
                if (lshCharUtil.isChineseChar(charItem)) {
                    num += 2;
                } else {
                    num += 1;
                }
            }
            String lineFont = "";
            int fontNum = (num + 1) / 2;
            for (int j = 0; j < fontNum; j++) {
                if (index % 4 == 0) {
                    lineFont += "超";
                } else if (index % 4 == 1) {
                    lineFont += "级";
                } else if (index % 4 == 2) {
                    lineFont += "码";
                } else if (index % 4 == 3) {
                    lineFont += "丽";
                }
                index++;
            }
            lines[i] = lineFont;
        }
        BufferedImage fontImage = new BufferedImage(videoInfo.getWidth(), videoInfo.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bgG2 = fontImage.createGraphics();//设置透明  start
        fontImage = bgG2.getDeviceConfiguration().createCompatibleImage(videoInfo.getWidth(), videoInfo.getHeight(), Transparency.TRANSLUCENT);
        bgG2 = fontImage.createGraphics();
        bgG2.setColor(getColor(videoFont.getColor()));
        Font font = new Font("宋体", Font.PLAIN, videoFont.getSize());
        bgG2.setFont(font);
        bgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = bgG2.getFontMetrics(font);
        int massageWidth = getWidth(lines, fm);
        int massageHeight = getHeight(lines, videoFont.getSize());
        int xWidth = (int) ((float) (fontImage.getWidth() - massageWidth) * videoFont.getX() / (float) 100);
        int yHeight = (int) ((float) (fontImage.getHeight() - massageHeight) * videoFont.getY() / (float) 100 + videoFont.getSize() * 0.9);
        Map<Integer, ArrayList<Integer>> map = getInfo(lines, fm, videoFont.getLayout(), massageWidth, videoFont.getSize());
        for (int i = 0; i < lines.length; i++) {
            bgG2.drawString(lines[i], xWidth + map.get(i).get(0), yHeight + map.get(i).get(1));
        }
        // 释放对象
        bgG2.dispose();
        //加水印图片
        String newImagePath = videoFont.getPath().substring(0, videoFont.getPath().lastIndexOf(".")) + "_font.jpg";
        videoFont.setImagePath(newImagePath);
        if (!lshImageUtil.sendImage(newImagePath, fontImage, "png")) {
            return new Result(false, null, null, "输出视频失败");
        }
        if (!lshFfmpegUtil.videoFont(videoFont)) {
            return new Result(false, null, null, "输出视频失败");
        }
        File nowFontImage = new File(videoFont.getImagePath());
        if (nowFontImage.exists()) {
            nowFontImage.delete();
        }
        return new Result(true, videoFont.getNewPath(), "添加成功", null);
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

    private int getHeight(String[] lines, int size) {
        int lineSplie = (int) (size * 0.2);
        return (lines.length * size) + (lines.length - 1) * lineSplie;
    }

    private Map<Integer, ArrayList<Integer>> getInfo(String[] lines, FontMetrics fm, String layout, int massageWidth, int size) {
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            List<Integer> list = new ArrayList<>();
            if (layout.equals("center")) {
                list.add((massageWidth - fm.stringWidth(lines[i])) / 2);
            } else if (layout.equals("right")) {
                list.add(massageWidth - fm.stringWidth(lines[i]));
            } else {
                list.add(0);
            }
            list.add((int) (i * size * 1.2));
            map.put(i, (ArrayList<Integer>) list);
        }
        return map;
    }

}
