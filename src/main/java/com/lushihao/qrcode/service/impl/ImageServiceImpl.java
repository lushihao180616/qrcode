package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.image.WaterMark;
import com.lushihao.qrcode.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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

            //需要画水印的图片
            BufferedImage bg = ImageIO.read(new FileInputStream(waterMark.getPath()));
            Graphics2D bgG2 = bg.createGraphics();
            if (waterMark.isIfShowLogo()) {
                BufferedImage waterMarkImage = ImageIO.read(new FileInputStream(projectBasicInfo.getBusinessUrl() + "\\" + waterMark.getBusinessCode() + "\\logo.png"));
                bgG2.drawImage(waterMarkImage, waterMark.getX(), waterMark.getY(), waterMark.getWidth(), waterMark.getHeight(), null);
            }
            if (waterMark.isIfShowFont()) {
                //底部高度
                int coverHeight;
                if (bg.getWidth() * 1.2 < bg.getHeight()) {
                    coverHeight = (int) (bg.getHeight() * 0.08);
                } else {
                    coverHeight = (int) (bg.getHeight() * 0.04);
                }
                //获得水印
                BufferedImage logoImage = ImageIO.read(new FileInputStream(projectBasicInfo.getBusinessUrl() + "\\" + waterMark.getBusinessCode() + "\\logo.png"));
                //下侧小商标画上去
                BufferedImage logoBgImage = new BufferedImage(coverHeight, coverHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D logoBgG2 = logoBgImage.createGraphics();
                logoBgG2.setColor(Color.BLACK);
                logoBgG2.drawImage(logoImage, (int) (coverHeight * 0.1), (int) (coverHeight * 0.1), (int) (coverHeight * 0.8), (int) (coverHeight * 0.8), null);
                bgG2.drawImage(logoBgImage, 0, bg.getHeight() - coverHeight, coverHeight, coverHeight, null);
                //创建黑色遮罩层
                BufferedImage cover = new BufferedImage(bg.getWidth(), coverHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D coverG2 = (Graphics2D) cover.getGraphics();
                coverG2.setColor(Color.BLACK);
                coverG2.fillRect(0, 0, cover.getWidth(), cover.getHeight());
                //设置字体
                if (bg.getWidth() * 1.2 < bg.getHeight()) {
                    Font font = new Font("微软雅黑", Font.PLAIN, (int) (coverHeight * 0.2));
                    coverG2.setFont(font);              //设置字体
                    coverG2.setColor(Color.WHITE); //根据图片的背景设置水印颜色
                    coverG2.drawString("店名：『" + nowBusiness.getName() + "』", (int) (coverHeight * 0.15), (float) (coverHeight * 0.25));
                    coverG2.drawString("电话：『" + nowBusiness.getPhone() + "』", (int) (coverHeight * 0.15), (float) (coverHeight * 0.55));
                    coverG2.drawString("地址：『" + nowBusiness.getAddress() + "』", (int) (coverHeight * 0.15), (float) (coverHeight * 0.85));
                } else {
                    Font font = new Font("微软雅黑", Font.PLAIN, (int) (coverHeight * 0.5));
                    coverG2.setFont(font);              //设置字体
                    coverG2.setColor(Color.WHITE); //根据图片的背景设置水印颜色
                    String name = "店名：『" + nowBusiness.getName() + "』";
                    String phone = "电话：『" + nowBusiness.getPhone() + "』";
                    coverG2.drawString(name, (int) (coverHeight * 0.15), (float) (coverHeight * 0.7));
                    coverG2.drawString(phone, (float) ((name.length()) * (coverHeight * 0.5) + (coverHeight * 0.65)), (float) (coverHeight * 0.7));
                    coverG2.drawString("地址：『" + nowBusiness.getAddress() + "』", (float) ((name.length() + phone.length() - 5) * (coverHeight * 0.5) + (coverHeight * 1.45)), (float) (coverHeight * 0.7));
                }
                coverG2.dispose();

                //遮罩层半透明绘制在图片上
                bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
                bgG2.drawImage(cover, coverHeight, bg.getHeight() - coverHeight, cover.getWidth(), coverHeight, null);
                bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            }
            bgG2.dispose();

            FileOutputStream outImgStream = new FileOutputStream(waterMark.getPath().substring(0, waterMark.getPath().lastIndexOf(".jpg")) + "_watermark.jpg");
            ImageIO.write(bg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (IOException e) {
            return "文件不存在";
        }
        return "添加成功，加水印图片被导出到" + waterMark.getPath().substring(0, waterMark.getPath().lastIndexOf("\\")) + "（原图片目录）下";
    }

}
