package com.lushihao.qrcode.service.image.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.dao.ManagerMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageWaterMark;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.image.ImageWaterMarkService;
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
public class ImageWaterMarkServiceImpl implements ImageWaterMarkService {

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
     * @param imageWaterMark
     * @return
     */
    @Override
    @Transactional
    public Result addWaterMark(ImageWaterMark imageWaterMark) {
        String[] lines = new String[3];
        //获取商家
        Business business = new Business();
        business.setCode(imageWaterMark.getBusinessCode());
        List<Business> list = businessMapper.filter(business);
        if (list.size() == 1) {
            business = list.get(0);
            lines[0] = "名称：『" + business.getName() + "』";
            lines[1] = "电话：『" + business.getPhone() + "』";
            lines[2] = "地址：『" + business.getAddress() + "』";
        } else {
            return new Result(false, null, null, "商家不存在");
        }

        //需要画水印的图片
        BufferedImage bg = lshImageUtil.getImage(imageWaterMark.getPath());
        int width = bg.getWidth();
        int height = bg.getHeight();
        int waterMarkHeight = (int) ((float) height * imageWaterMark.getHeight() / (float) 100);
        int fontSize = (int) (waterMarkHeight * 0.2);
        int offSet = (int) (waterMarkHeight * 0.05);
        Graphics2D bgG2 = bg.createGraphics();
        bgG2.setColor(getColor(imageWaterMark.getFontColor()));
        Font font = new Font("宋体", Font.PLAIN, fontSize);
        bgG2.setFont(font);
        bgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = bgG2.getFontMetrics(font);
        int waterMarkWidth = getWidth(lines, fm) + waterMarkHeight;
        int xWidth = (int) ((float) (width - waterMarkWidth) * imageWaterMark.getX() / (float) 100);
        int yHeight = (int) ((float) (height - waterMarkHeight) * imageWaterMark.getY() / (float) 100);
        bgG2.drawString(lines[0], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 1.5));
        bgG2.drawString(lines[1], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 3.0));
        bgG2.drawString(lines[2], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 4.5));

        BufferedImage logoImage = lshImageUtil.getImage(projectBasicInfo.getBusinessUrl() + "\\" + imageWaterMark.getBusinessCode() + "\\logo.png");
        if (logoImage == null) {
            return new Result(false, null, null, "商标不存在");
        }
        logoImage = roundImage(logoImage, logoImage.getWidth(), logoImage.getWidth());
        bgG2.drawImage(logoImage, xWidth + offSet, yHeight + offSet, waterMarkHeight - offSet, waterMarkHeight - offSet, null);
        bgG2.dispose();

        //删除测试文件
        File testFile = new File(imageWaterMark.getPath().substring(0, imageWaterMark.getPath().lastIndexOf(".")) + "_test.jpg");
        if (testFile.exists()) {
            testFile.delete();
        }
        //加水印图片
        String newImagePath = imageWaterMark.getPath().substring(0, imageWaterMark.getPath().lastIndexOf(".")) + "_waterMark.jpg";
        if (!lshImageUtil.sendImage(newImagePath, bg)) {
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, newImagePath, "添加成功", null);
    }

    /**
     * 添加水印
     *
     * @param imageWaterMark
     * @return
     */
    @Override
    @Transactional
    public Result testWaterMark(ImageWaterMark imageWaterMark) {
        String[] lines = new String[3];
        //获取商家
        Manager manager = new Manager();
        manager.setCode(imageWaterMark.getBusinessCode());
        List<Manager> list = managerMapper.filter(manager);
        if (list.size() == 1) {
            manager = list.get(0);
            lines[0] = "名称：『" + manager.getName() + "』";
            lines[1] = "电话：『" + manager.getPhone() + "』";
            lines[2] = "地址：『" + manager.getAddress() + "』";
        } else {
            return new Result(false, null, null, "管理员不存在");
        }

        //需要画水印的图片
        BufferedImage bg = lshImageUtil.getImage(imageWaterMark.getPath());
        int width = bg.getWidth();
        int height = bg.getHeight();
        int waterMarkHeight = (int) ((float) height * imageWaterMark.getHeight() / (float) 100);
        int fontSize = (int) (waterMarkHeight * 0.2);
        int offSet = (int) (waterMarkHeight * 0.05);
        Graphics2D bgG2 = bg.createGraphics();
        bgG2.setColor(getColor(imageWaterMark.getFontColor()));
        Font font = new Font("宋体", Font.PLAIN, fontSize);
        bgG2.setFont(font);
        bgG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = bgG2.getFontMetrics(font);
        int waterMarkWidth = getWidth(lines, fm) + waterMarkHeight;
        int xWidth = (int) ((float) (width - waterMarkWidth) * imageWaterMark.getX() / (float) 100);
        int yHeight = (int) ((float) (height - waterMarkHeight) * imageWaterMark.getY() / (float) 100);
        bgG2.drawString(lines[0], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 1.5));
        bgG2.drawString(lines[1], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 3.0));
        bgG2.drawString(lines[2], xWidth + waterMarkHeight + offSet, (float) (yHeight + fontSize * 4.5));

        BufferedImage logoImage = lshImageUtil.getImage(projectBasicInfo.getBusinessUrl() + "\\" + imageWaterMark.getManagerCode() + "\\logo.png");
        if (logoImage == null) {
            return new Result(false, null, null, "商标不存在");
        }
        logoImage = roundImage(logoImage, logoImage.getWidth(), logoImage.getWidth());
        bgG2.drawImage(logoImage, xWidth + offSet, yHeight + offSet, waterMarkHeight - offSet, waterMarkHeight - offSet, null);
        bgG2.dispose();

        //加水印图片
        String newImagePath = imageWaterMark.getPath().substring(0, imageWaterMark.getPath().lastIndexOf(".")) + "_test.jpg";
        if (!lshImageUtil.sendImage(newImagePath, bg)) {
            return new Result(false, null, null, "输出图片失败");
        }
        return new Result(true, newImagePath, "添加成功", null);
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
