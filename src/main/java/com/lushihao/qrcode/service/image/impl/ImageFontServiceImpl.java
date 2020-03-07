package com.lushihao.qrcode.service.image.impl;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageFont;
import com.lushihao.qrcode.service.image.ImageFontService;
import com.lushihao.qrcode.util.LSHCharUtil;
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
public class ImageFontServiceImpl implements ImageFontService {

    @Resource
    private LSHImageUtil lshImageUtil;
    @Resource
    private LSHCharUtil lshCharUtil;

    @Override
    public Result addFont(ImageFont imageFont) {
        BufferedImage bg = lshImageUtil.getImage(imageFont.getPath());
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }
        if (!lshCharUtil.isDefaultChar(imageFont.getMessage())) {
            return new Result(false, null, null, "只支持汉字、英文字母、数字");
        }
        String[] lines = imageFont.getMessage().split("\\n");
        Graphics2D bgG2 = bg.createGraphics();
        bgG2.setColor(getColor(imageFont.getColor()));
        Font font = new Font("宋体", Font.PLAIN, imageFont.getSize());
        bgG2.setFont(font);
        bgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = bgG2.getFontMetrics(font);
        int massageWidth = getWidth(lines, fm);
        int massageHeight = getHeight(lines, imageFont.getSize());
        int xWidth = (int) ((float) (bg.getWidth() - massageWidth) * imageFont.getX() / (float) 100);
        int yHeight = (int) ((float) (bg.getHeight() - massageHeight) * imageFont.getY() / (float) 100 + imageFont.getSize() * 0.9);
        Map<Integer, ArrayList<Integer>> map = getInfo(lines, fm, imageFont.getLayout(), massageWidth, imageFont.getSize());
        for (int i = 0; i < lines.length; i++) {
            bgG2.drawString(lines[i], xWidth + map.get(i).get(0), yHeight + map.get(i).get(1));
        }
        // 释放对象
        bgG2.dispose();
        //删除测试文件
        File testFile = new File(imageFont.getPath().substring(0, imageFont.getPath().lastIndexOf(".")) + "_test.jpg");
        if (testFile.exists()) {
            testFile.delete();
        }
        //加水印图片
        String newImagePath = imageFont.getPath().substring(0, imageFont.getPath().lastIndexOf(".")) + "_font.jpg";
        if (!lshImageUtil.sendImage(newImagePath, bg)) {
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, newImagePath, "添加成功", null);
    }

    @Override
    public Result testFont(ImageFont imageFont) {
        BufferedImage bg = lshImageUtil.getImage(imageFont.getPath());
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }
        String[] lines = imageFont.getMessage().split("\\n");
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
        Graphics2D bgG2 = bg.createGraphics();
        bgG2.setColor(getColor(imageFont.getColor()));
        Font font = new Font("宋体", Font.PLAIN, imageFont.getSize());
        bgG2.setFont(font);
        bgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = bgG2.getFontMetrics(font);
        int massageWidth = getWidth(lines, fm);
        int massageHeight = getHeight(lines, imageFont.getSize());
        int xWidth = (int) ((float) (bg.getWidth() - massageWidth) * imageFont.getX() / (float) 100);
        int yHeight = (int) ((float) (bg.getHeight() - massageHeight) * imageFont.getY() / (float) 100 + imageFont.getSize() * 0.9);
        Map<Integer, ArrayList<Integer>> map = getInfo(lines, fm, imageFont.getLayout(), massageWidth, imageFont.getSize());
        for (int i = 0; i < lines.length; i++) {
            bgG2.drawString(lines[i], xWidth + map.get(i).get(0), yHeight + map.get(i).get(1));
        }
        // 释放对象
        bgG2.dispose();
        //加水印图片
        String testImagePath = imageFont.getPath().substring(0, imageFont.getPath().lastIndexOf(".")) + "_test.jpg";
        if (!lshImageUtil.sendImage(testImagePath, bg)) {
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, testImagePath, "添加成功", null);
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
