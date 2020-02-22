package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.image.WaterMark;
import com.lushihao.qrcode.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;

    /**
     * 添加水印
     *
     * @param waterMark
     * @return
     */
    @Override
    @Transactional
    public String addWaterMark(WaterMark waterMark) {
        try {
            //获取商家
            Business business = new Business();
            business.setCode(waterMark.getBusinessCode());
            Business nowBusiness = businessMapper.filter(business).get(0);

            BufferedImage bg = ImageIO.read(new FileInputStream(waterMark.getPath()));
            int width = bg.getWidth();
            int height = bg.getHeight();
            int waterMarkHeight = height * waterMark.getHeightPercentage() / 100;
            int fontSize = (int) (waterMarkHeight * 0.3);
            int offSet = (int) (waterMarkHeight * 0.05);
            int waterMarkWidth = (nowBusiness.getAddress().length() + 5) * fontSize + waterMarkHeight;

            //需要画水印的图片
            BufferedImage waterMarkImage = new BufferedImage(waterMarkWidth, waterMarkHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D waterMarkG2 = waterMarkImage.createGraphics();
            waterMarkImage = waterMarkG2.getDeviceConfiguration().createCompatibleImage(waterMarkWidth, waterMarkHeight, Transparency.TRANSLUCENT);
            waterMarkG2 = waterMarkImage.createGraphics();
            BufferedImage logoImage = ImageIO.read(new FileInputStream(projectBasicInfo.getBusinessUrl() + "\\" + waterMark.getBusinessCode() + "\\logo.png"));
            waterMarkG2.drawImage(logoImage, offSet, offSet, waterMarkHeight - offSet, waterMarkHeight - offSet, null);

            Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
            waterMarkG2.setFont(font);              //设置字体
            waterMarkG2.setColor(Color.WHITE); //根据图片的背景设置水印颜色
            waterMarkG2.drawString("店名：『" + nowBusiness.getName() + "』", waterMarkHeight + offSet, (float) (fontSize * 1.1));
            waterMarkG2.drawString("电话：『" + nowBusiness.getPhone() + "』", waterMarkHeight + offSet, (float) (fontSize * 2.1));
            waterMarkG2.drawString("地址：『" + nowBusiness.getAddress() + "』", waterMarkHeight + offSet, (float) (fontSize * 3.1));
            waterMarkG2.dispose();

            //遮罩层半透明绘制在图片上
            Graphics2D bgG2 = bg.createGraphics();
            bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) waterMark.getAlpha() / (float) 100));
            bgG2.drawImage(waterMarkImage, waterMark.getxPercentage() * (width - waterMarkWidth) / 100, waterMark.getyPercentage() * (height - waterMarkHeight) / 100, waterMarkWidth, waterMarkHeight, null);
            bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            bgG2.dispose();

            FileOutputStream outImgStream = new FileOutputStream(waterMark.getPath().substring(0, waterMark.getPath().lastIndexOf(".jpg")) + "_watermark.jpg");
            ImageIO.write(bg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (IOException e) {
            return "添加失败";
        }
        return "添加成功";
    }

}
