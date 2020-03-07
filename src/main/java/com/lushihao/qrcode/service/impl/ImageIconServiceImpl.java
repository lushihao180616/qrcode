package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageIcon;
import com.lushihao.qrcode.service.ImageIconService;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class ImageIconServiceImpl implements ImageIconService {

    @Resource
    private LSHImageUtil lshImageUtil;

    @Override
    public Result addIcon(ImageIcon imageIcon) {
        BufferedImage bg = lshImageUtil.getImage(imageIcon.getPath());
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }
        BufferedImage icon = lshImageUtil.getImage(imageIcon.getIcon());
        if (icon == null) {
            return new Result(false, null, null, "添加图标不存在");
        }
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();
        int iconWidth = (int) ((float) bgWidth * imageIcon.getWidth() / (float) 100);
        int iconHeight = (int) ((float) bgHeight * imageIcon.getHeight() / (float) 100);
        int xWidth = (int) ((float) (bgWidth - iconWidth) * imageIcon.getX() / (float) 100);
        int yHeight = (int) ((float) (bgHeight - iconHeight) * imageIcon.getY() / (float) 100);
        Graphics2D bgG2 = bg.createGraphics();
        bgG2.drawImage(icon, xWidth, yHeight, iconWidth, iconHeight, null);
        bgG2.dispose();
        //删除测试文件
        File testFile = new File(imageIcon.getPath().substring(0, imageIcon.getPath().lastIndexOf(".")) + "_test.jpg");
        if (testFile.exists()) {
            testFile.delete();
        }
        //加水印图片
        String newImagePath = imageIcon.getPath().substring(0, imageIcon.getPath().lastIndexOf(".")) + "_icon.jpg";
        if (!lshImageUtil.sendImage(newImagePath, bg)) {
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, newImagePath, "添加成功", null);
    }

    @Override
    public Result testIcon(ImageIcon imageIcon) {
        BufferedImage bg = lshImageUtil.getImage(imageIcon.getPath());
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
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();
        int iconWidth = (int) ((float) bgWidth * imageIcon.getWidth() / (float) 100);
        int iconHeight = (int) ((float) bgHeight * imageIcon.getHeight() / (float) 100);
        int xWidth = (int) ((float) (bgWidth - iconWidth) * imageIcon.getX() / (float) 100);
        int yHeight = (int) ((float) (bgHeight - iconHeight) * imageIcon.getY() / (float) 100);
        Graphics2D bgG2 = bg.createGraphics();
        bgG2.drawImage(icon, xWidth, yHeight, iconWidth, iconHeight, null);
        bgG2.dispose();
        //加水印图片
        String testImagePath = imageIcon.getPath().substring(0, imageIcon.getPath().lastIndexOf(".")) + "_test.jpg";
        if (!lshImageUtil.sendImage(testImagePath, bg)) {
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, testImagePath, "添加成功", null);
    }

}
