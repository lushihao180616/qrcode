package com.lushihao.qrcode.service.image.impl;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageCut;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.entity.yml.UserBasicInfo;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.image.ImageCutService;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Collectors;

@Service
public class ImageCutServiceImpl implements ImageCutService {

    @Resource
    private LSHImageUtil lshImageUtil;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserBasicInfo userBasicInfo;
    @Resource
    private InitProject initProject;

    @Override
    public Result addCut(ImageCut imageCut) {
        BufferedImage bg = lshImageUtil.getImage(imageCut.getPath());
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();
        int cutWidth = bgWidth * imageCut.getWidth() / 100;
        int cutHeight = bgHeight * imageCut.getHeight() / 100;
        int xWidth = (bgWidth - cutWidth) * imageCut.getX() / 100;
        int yHeight = (bgWidth - cutWidth) * imageCut.getY() / 100;
        //截取的图片
        BufferedImage cutImage = new BufferedImage(cutWidth, cutHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D cutImageG2 = cutImage.createGraphics();
        cutImageG2.setBackground(Color.WHITE);
        cutImageG2.clearRect(0, 0, cutWidth, cutHeight);
        cutImageG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 1 - (float) imageCut.getAlpha() / 100));
        cutImageG2.drawImage(bg, -xWidth, -yHeight, bg.getWidth(), bg.getHeight(), null);
        cutImageG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        cutImageG2.dispose();
        //删除测试文件
        File testFile = new File(imageCut.getPath().substring(0, imageCut.getPath().lastIndexOf(".")) + "_test.jpg");
        if (testFile.exists()) {
            testFile.delete();
        }
        if (!userInfoService.countSub(initProject.beanCosts.stream().filter(s -> s.getType().equals("imagecut")).collect(Collectors.toList()).get(0).getBean(), userBasicInfo.getCode())) {
            return new Result(false, null, null, "金豆不够用了");
        }
        //加水印图片
        String newImagePath = imageCut.getPath().substring(0, imageCut.getPath().lastIndexOf(".")) + "_cut.jpg";
        if (!lshImageUtil.sendImage(newImagePath, cutImage)) {
            userInfoService.countAdd(initProject.beanCosts.stream().filter(s -> s.getType().equals("imagecut")).collect(Collectors.toList()).get(0).getBean(), userBasicInfo.getCode());
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, newImagePath, "添加成功", null);
    }

    @Override
    public Result testCut(ImageCut imageCut) {
        BufferedImage bg = lshImageUtil.getImage(imageCut.getPath());
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }
        int bgWidth = bg.getWidth();
        int bgHeight = bg.getHeight();
        int cutWidth = bgWidth * imageCut.getWidth() / 100;
        int cutHeight = bgHeight * imageCut.getHeight() / 100;
        int xWidth = (bgWidth - cutWidth) * imageCut.getX() / 100;
        int yHeight = (bgWidth - cutWidth) * imageCut.getY() / 100;
        //截取的图片
        BufferedImage cutImage = new BufferedImage(cutWidth, cutHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D cutImageG2 = cutImage.createGraphics();
        cutImageG2.setBackground(Color.WHITE);
        cutImageG2.clearRect(0, 0, cutWidth, cutHeight);
        cutImageG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 1 - (float) imageCut.getAlpha() / 100));
        cutImageG2.drawImage(bg, -xWidth, -yHeight, bg.getWidth(), bg.getHeight(), null);
        cutImageG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        cutImageG2.dispose();
        //需要画水印的图片
        BufferedImage outPutImage = new BufferedImage(bgWidth, bgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D outPutImageG2 = outPutImage.createGraphics();
        outPutImageG2.setBackground(Color.WHITE);
        outPutImageG2.clearRect(0, 0, bgWidth, bgHeight);
        outPutImageG2.drawImage(cutImage, xWidth, yHeight, cutWidth, cutHeight, null);
        outPutImageG2.dispose();
        //加水印图片
        String testImagePath = imageCut.getPath().substring(0, imageCut.getPath().lastIndexOf(".")) + "_test.jpg";
        if (!lshImageUtil.sendImage(testImagePath, outPutImage)) {
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, testImagePath, "添加成功", null);
    }

}
