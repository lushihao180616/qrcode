package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.dao.ManagerMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.WaterMark;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.WaterMarkService;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Service
public class WaterMarkServiceImpl implements WaterMarkService {

    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private ManagerMapper managerMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;
    @Resource
    private LSHImageUtil lshImageUtil;

    /**
     * 添加水印
     *
     * @param waterMark
     * @return
     */
    @Override
    @Transactional
    public Result addWaterMark(WaterMark waterMark) {
        //获取商家
        Business business = new Business();
        business.setCode(waterMark.getBusinessCode());
        List<Business> list = businessMapper.filter(business);
        if (list.size() == 1) {
            business = list.get(0);
        } else {
            return new Result(false, null, null, "商家不存在");
        }

        int fontLength = 7;
        fontLength = business.getName().length() > fontLength ? business.getName().length() : fontLength;
        fontLength = business.getAddress().length() > fontLength ? business.getAddress().length() : fontLength;
        BufferedImage bg = lshImageUtil.getImage(waterMark.getPath());
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }
        int width = bg.getWidth();
        int height = bg.getHeight();
        int waterMarkHeight = height * waterMark.getHeightPercentage() / 100;
        int fontSize = (int) (waterMarkHeight * 0.2);
        int offSet = (int) (waterMarkHeight * 0.05);
        int waterMarkWidth = (fontLength + 5) * fontSize + waterMarkHeight;

        //需要画水印的图片
        BufferedImage waterMarkImage = new BufferedImage(waterMarkWidth, waterMarkHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D waterMarkG2 = waterMarkImage.createGraphics();
        waterMarkImage = waterMarkG2.getDeviceConfiguration().createCompatibleImage(waterMarkWidth, waterMarkHeight, Transparency.TRANSLUCENT);
        waterMarkG2 = waterMarkImage.createGraphics();
        BufferedImage logoImage = lshImageUtil.getImage(projectBasicInfo.getBusinessUrl() + "\\" + waterMark.getBusinessCode() + "\\logo.png");
        if (logoImage == null) {
            return new Result(false, null, null, "商标不存在");
        }
        logoImage = roundImage(logoImage, logoImage.getWidth(), logoImage.getWidth());
        waterMarkG2.drawImage(logoImage, offSet, offSet, waterMarkHeight - offSet, waterMarkHeight - offSet, null);

        Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
        waterMarkG2.setFont(font);              //设置字体
        waterMarkG2.setColor(Color.WHITE); //根据图片的背景设置水印颜色
        waterMarkG2.drawString("店名：『" + business.getName() + "』", waterMarkHeight + offSet, (float) (fontSize * 1.5));
        waterMarkG2.drawString("电话：『" + business.getPhone() + "』", waterMarkHeight + offSet, (float) (fontSize * 3.0));
        waterMarkG2.drawString("地址：『" + business.getAddress() + "』", waterMarkHeight + offSet, (float) (fontSize * 4.5));
        waterMarkG2.dispose();

        //遮罩层半透明绘制在图片上
        Graphics2D bgG2 = bg.createGraphics();
        bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 1 - (float) waterMark.getAlpha() / 100));
        bgG2.drawImage(waterMarkImage, waterMark.getxPercentage() * (width - waterMarkWidth) / 100, waterMark.getyPercentage() * (height - waterMarkHeight) / 100, waterMarkWidth, waterMarkHeight, null);
        bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        bgG2.dispose();

        //删除测试文件
        File testFile = new File(waterMark.getPath().substring(0, waterMark.getPath().lastIndexOf(".")) + "_test.jpg");
        if (testFile.exists()) {
            testFile.delete();
        }
        //加水印图片
        String newImagePath = waterMark.getPath().substring(0, waterMark.getPath().lastIndexOf(".")) + "_watermark.jpg";
        if (!lshImageUtil.sendImage(newImagePath, bg)) {
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, newImagePath, "添加成功", null);
    }

    /**
     * 添加水印
     *
     * @param waterMark
     * @return
     */
    @Override
    @Transactional
    public Result testWaterMark(WaterMark waterMark) {
        boolean ifOverFlow = false;
        //获取商家
        Manager manager = new Manager();
        manager.setCode(waterMark.getManagerCode());
        List<Manager> list = managerMapper.filter(manager);
        if (list.size() == 1) {
            manager = list.get(0);
        } else {
            return new Result(false, null, null, "管理员不存在");
        }

        int fontLength = 7;
        fontLength = manager.getName().length() > fontLength ? manager.getName().length() : fontLength;
        fontLength = manager.getAddress().length() > fontLength ? manager.getAddress().length() : fontLength;
        BufferedImage bg = lshImageUtil.getImage(waterMark.getPath());
        if (bg == null) {
            return new Result(false, null, null, "背景图片不存在");
        }

        int width = bg.getWidth();
        int height = bg.getHeight();
        int waterMarkHeight = height * waterMark.getHeightPercentage() / 100;
        int fontSize = (int) (waterMarkHeight * 0.2);
        int offSet = (int) (waterMarkHeight * 0.05);
        int waterMarkWidth = (fontLength + 5) * fontSize + waterMarkHeight;

        //需要画水印的图片
        BufferedImage waterMarkImage = new BufferedImage(waterMarkWidth, waterMarkHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D waterMarkG2 = waterMarkImage.createGraphics();
        waterMarkImage = waterMarkG2.getDeviceConfiguration().createCompatibleImage(waterMarkWidth, waterMarkHeight, Transparency.TRANSLUCENT);
        waterMarkG2 = waterMarkImage.createGraphics();
        BufferedImage logoImage = lshImageUtil.getImage(projectBasicInfo.getBusinessUrl() + "\\" + waterMark.getManagerCode() + "\\logo.png");
        if (logoImage == null) {
            return new Result(false, null, null, "商标不存在");
        }
        logoImage = roundImage(logoImage, logoImage.getWidth(), logoImage.getWidth());
        waterMarkG2.drawImage(logoImage, offSet, offSet, waterMarkHeight - offSet, waterMarkHeight - offSet, null);

        Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
        waterMarkG2.setFont(font);              //设置字体
        waterMarkG2.setColor(Color.WHITE); //根据图片的背景设置水印颜色
        waterMarkG2.drawString("名称：『" + manager.getName() + "』", waterMarkHeight + offSet, (float) (fontSize * 1.5));
        waterMarkG2.drawString("电话：『" + manager.getPhone() + "』", waterMarkHeight + offSet, (float) (fontSize * 3.0));
        waterMarkG2.drawString("地址：『" + manager.getAddress() + "』", waterMarkHeight + offSet, (float) (fontSize * 4.5));
        waterMarkG2.dispose();

        //遮罩层半透明绘制在图片上
        Graphics2D bgG2 = bg.createGraphics();
        bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 1 - (float) waterMark.getAlpha() / 100));
        bgG2.drawImage(waterMarkImage, waterMark.getxPercentage() * (width - waterMarkWidth) / 100, waterMark.getyPercentage() * (height - waterMarkHeight) / 100, waterMarkWidth, waterMarkHeight, null);
        bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        bgG2.dispose();

        //测试图片
        String testImagePath = waterMark.getPath().substring(0, waterMark.getPath().lastIndexOf(".")) + "_test.jpg";
        if (!lshImageUtil.sendImage(testImagePath, bg)) {
            return new Result(false, null, null, "输出图片失败");
        }
        if (waterMarkWidth > width) {
            ifOverFlow = true;
        }
        if (ifOverFlow) {
            return new Result(false, null, null, "信息不能完全展示，请尝试将高度调小");
        } else {
            return new Result(true, testImagePath, "添加成功", null);
        }
    }

    /**
     * 图片加圆角
     *
     * @param image
     * @param targetSize
     * @param cornerRadius
     * @return
     */
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
