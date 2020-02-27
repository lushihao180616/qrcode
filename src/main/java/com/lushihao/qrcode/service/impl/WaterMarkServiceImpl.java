package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.image.WaterMark;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.WaterMarkService;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class WaterMarkServiceImpl implements WaterMarkService {

    @Resource
    private BusinessMapper businessMapper;
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
    public String addWaterMark(WaterMark waterMark) {
        FileInputStream bgInputStream = null;
        FileInputStream logoInputStream = null;
        FileOutputStream outImgStream = null;
        //获取商家
        Business business = new Business();
        business.setCode(waterMark.getBusinessCode());
        Business nowBusiness = businessMapper.filter(business).get(0);

        BufferedImage bg = lshImageUtil.getImage(waterMark.getPath());
        int width = bg.getWidth();
        int height = bg.getHeight();
        int waterMarkHeight = height * waterMark.getHeightPercentage() / 100;
        int fontSize = (int) (waterMarkHeight * 0.2);
        int offSet = (int) (waterMarkHeight * 0.05);
        int waterMarkWidth = (nowBusiness.getAddress().length() + 5) * fontSize + waterMarkHeight;

        //需要画水印的图片
        BufferedImage waterMarkImage = new BufferedImage(waterMarkWidth, waterMarkHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D waterMarkG2 = waterMarkImage.createGraphics();
        waterMarkImage = waterMarkG2.getDeviceConfiguration().createCompatibleImage(waterMarkWidth, waterMarkHeight, Transparency.TRANSLUCENT);
        waterMarkG2 = waterMarkImage.createGraphics();
        BufferedImage logoImage = lshImageUtil.getImage(projectBasicInfo.getBusinessUrl() + "\\" + waterMark.getBusinessCode() + "\\logo.png");
        logoImage = roundImage(logoImage, logoImage.getWidth(), logoImage.getWidth());
        waterMarkG2.drawImage(logoImage, offSet, offSet, waterMarkHeight - offSet, waterMarkHeight - offSet, null);

        Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
        waterMarkG2.setFont(font);              //设置字体
        waterMarkG2.setColor(Color.WHITE); //根据图片的背景设置水印颜色
        waterMarkG2.drawString("店名：『" + nowBusiness.getName() + "』", waterMarkHeight + offSet, (float) (fontSize * 1.5));
        waterMarkG2.drawString("电话：『" + nowBusiness.getPhone() + "』", waterMarkHeight + offSet, (float) (fontSize * 3.0));
        waterMarkG2.drawString("地址：『" + nowBusiness.getAddress() + "』", waterMarkHeight + offSet, (float) (fontSize * 4.5));
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
        lshImageUtil.sendImage(newImagePath, bg);
        return "添加成功";
    }

    /**
     * 添加水印
     *
     * @param waterMark
     * @return
     */
    @Override
    @Transactional
    public String testWaterMark(WaterMark waterMark) {
        boolean ifOverFlow = false;
        FileInputStream bgInputStream = null;
        FileOutputStream outImgStream = null;
        try {
            //获取商家
            Business business = new Business();
            business.setCode(waterMark.getBusinessCode());
            Business nowBusiness = businessMapper.filter(business).get(0);

            bgInputStream = new FileInputStream(waterMark.getPath());
            BufferedImage bg = ImageIO.read(bgInputStream);
            int width = bg.getWidth();
            int height = bg.getHeight();
            int waterMarkHeight = height * waterMark.getHeightPercentage() / 100;
            int fontSize = (int) (waterMarkHeight * 0.3);
            int waterMarkWidth = (nowBusiness.getAddress().length() + 5) * fontSize + waterMarkHeight;

            //需要画水印的图片
            BufferedImage waterMarkImage = new BufferedImage(waterMarkWidth, waterMarkHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D waterMarkG2 = waterMarkImage.createGraphics();
            waterMarkG2.setBackground(Color.WHITE);
            waterMarkG2.clearRect(0, 0, waterMarkWidth, waterMarkHeight);
            Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
            waterMarkG2.setFont(font);              //设置字体
            waterMarkG2.setColor(Color.BLACK);
            waterMarkG2.drawString("信息", (float) (waterMarkWidth / 2 + waterMarkHeight / 2 - fontSize), (float) (waterMarkHeight * 0.35 + fontSize));
            BufferedImage logoImage = new BufferedImage(waterMarkHeight, waterMarkHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D logoG2 = logoImage.createGraphics();
            logoG2.setBackground(Color.BLACK);
            logoG2.clearRect(0, 0, waterMarkHeight, waterMarkHeight);
            Font font2 = new Font("微软雅黑", Font.PLAIN, fontSize);
            logoG2.setFont(font2);              //设置字体
            logoG2.setColor(Color.WHITE);
            logoG2.drawString("头像", (float) (waterMarkHeight * 0.2), (float) (waterMarkHeight * 0.35 + fontSize));
            waterMarkG2.drawImage(logoImage, 0, 0, waterMarkHeight, waterMarkHeight, null);

            //遮罩层半透明绘制在图片上
            Graphics2D bgG2 = bg.createGraphics();
            bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 1 - (float) waterMark.getAlpha() / 100));
            bgG2.drawImage(waterMarkImage, waterMark.getxPercentage() * (width - waterMarkWidth) / 100, waterMark.getyPercentage() * (height - waterMarkHeight) / 100, waterMarkWidth, waterMarkHeight, null);
            bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            bgG2.dispose();

            String testImagePath = waterMark.getPath().substring(0, waterMark.getPath().lastIndexOf(".")) + "_test.jpg";
            outImgStream = new FileOutputStream(testImagePath);
            ImageIO.write(bg, "jpg", outImgStream);

            if (waterMarkWidth > width) {
                ifOverFlow = true;
            }
        } catch (IOException e) {
            return "添加失败";
        } finally {
            try {
                if (outImgStream != null) {
                    outImgStream.flush();
                    outImgStream.close();
                }
                if (bgInputStream != null) {
                    bgInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ifOverFlow) {
            return "添加成功，但是信息不能完全显示，请尝试将高度调小";
        } else {
            return "添加成功";
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
