package com.lushihao.qrcode.util;

import com.lushihao.myutils.time.LSHDateUtils;
import com.lushihao.qrcode.dao.QRCodeRecordMapper;
import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeVo;
import com.swetake.util.Qrcode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * QrcodeText 二维码
 *
 * @author krry
 * @version 1.0
 */
@Component
public class LSHQRCodeUtil {

    @Resource
    private ProjectBasicInfo projectBasicInfo;
    @Resource
    private QRCodeRecordMapper qrCodeRecordMapper;

    private int width = 975;
    private int height = 975;

    // 设置偏移量，不设置可能导致解析出错
    private int pixoff = 25;
    // 像素大小
    private int pix = 25;
    // 二维码数组的长度
    private int codeLength;
    // 随机数，生成[0,2]之间的随机整数,取长度为3的数组下标
    private int max = 3;

    //素材图片容器
    private BufferedImage image_eye;
    private BufferedImage image01;
    private BufferedImage image02;
    private BufferedImage image03;
    private BufferedImage image11;
    private BufferedImage image12;
    private BufferedImage image13;
    private BufferedImage image21;
    private BufferedImage image22;
    private BufferedImage image23;
    private BufferedImage image31;
    private BufferedImage image32;
    private BufferedImage image33;
    private BufferedImage image41;
    private BufferedImage image42;
    private BufferedImage image43;
    private BufferedImage imageBG;
    private BufferedImage imageLogo;
    private BufferedImage imageLogoBorder;

    /**
     * 生成二维码
     *
     * @param qrCodeVo 二维码相关信息
     * @return
     */
    public boolean qrcode(QRCodeVo qrCodeVo) {
        FileOutputStream outputStream = null;

        try {
            //创建二维码对象
            Qrcode qrcode = new Qrcode();
            //设置二维码的纠错级别
            //L(7%) M(15%) Q(25%) H(30%)
            qrcode.setQrcodeErrorCorrect('L'); //一般纠错级别小一点
            //设置二维码的编码模式 Binary(按照字节编码模式)
            qrcode.setQrcodeEncodeMode('B');
            //设置二维码的版本号 1-40  1:20*21    2:25*25   40:177*177
            qrcode.setQrcodeVersion(5);

            //获取图片缓存流对象
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //获取画笔
            Graphics2D gs = image.createGraphics();
            //判断是否使用二维码背景颜色是透明
            if (!qrCodeVo.getTypeCode().isIfOnly()) {
                //设置透明
                image = gs.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
                gs = image.createGraphics();
            } else {
                gs.setBackground(Color.WHITE);
                gs.clearRect(0, 0, width, height);
            }

            //设置内容
            String content = qrCodeVo.getMessage();
            byte[] contentsBytes = content.getBytes("utf-8");

            //二维码
            boolean[][] code = qrcode.calQrcode(contentsBytes);
            //获取二维码数组的长度
            codeLength = code.length;

            //码眼部分全部设置为false
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    code[i][j] = false;
                }
                for (int j = codeLength - 7; j < codeLength; j++) {
                    code[i][j] = false;
                    code[j][i] = false;
                }
            }

            //加载图片
            loadImage(qrCodeVo.getTypeCode().isIfOnly(), qrCodeVo.getTypeCode().isIfShowLogo(), qrCodeVo.getTypeCode().getCode(), qrCodeVo.getBusinessCode());

            //绘制二维码，选择算法
            if (qrCodeVo.getTypeCode().getArti().equals("0")) {
                drawQrcodeHot(gs, code); //热门算法
            } else if (qrCodeVo.getTypeCode().getArti().equals("1")) {
                drawQrcodeOrdi(gs, code); //最初算法
            }
            //添加logo
            if (qrCodeVo.getTypeCode().isIfShowLogo()) {
                gs.drawImage(imageLogoBorder, (width - 120) / 2, (height - 120) / 2, 120, 120, null);
                gs.drawImage(imageLogo, (width - 100) / 2, (height - 100) / 2, 100, 100, null);
            }
            //释放画笔
            gs.dispose();
            Graphics2D bg = null;
            //如果类型不是单码，则装载背景图片，将二维码写进背景图片中，只有单码没有背景
            if (!qrCodeVo.getTypeCode().isIfOnly()) {
                //获取图片缓存流对象
                BufferedImage backimage = new BufferedImage(qrCodeVo.getTypeCode().getWidth(), qrCodeVo.getTypeCode().getHeight(), BufferedImage.TYPE_INT_RGB);
                bg = backimage.createGraphics();
                bg.drawImage(imageBG, 0, 0, qrCodeVo.getTypeCode().getWidth(), qrCodeVo.getTypeCode().getHeight(), null);

                //位置坐标
                int x = qrCodeVo.getTypeCode().getX();
                int y = qrCodeVo.getTypeCode().getY();
                bg.drawImage(image, x, y, width, height, null);
                bg.dispose();
                image = backimage;
            }
            //生成二维码图片
            String qrcodePath = projectBasicInfo.getQrcodeUrl() + "\\" + qrCodeVo.getBusinessCode() + "\\" + new SimpleDateFormat("yyyy_MM_dd").format(new Date());
            File qrcodeDirectory = new File(qrcodePath);
            if (!qrcodeDirectory.exists()) {//如果文件夹不存在
                qrcodeDirectory.mkdir();//创建文件夹
            }
            String fileName;
            if (!qrCodeVo.getBusinessCode().equals("00000000")) {//商家创建
                fileName = new SimpleDateFormat("HH_mm_ss_").format(new Date()) + qrCodeVo.getFileName() + ".jpg";
                outputStream = new FileOutputStream(new File(qrcodePath + "\\", fileName));
            } else {//操作员创建
                fileName = qrCodeVo.getFileName() + ".jpg";
                outputStream = new FileOutputStream(new File(projectBasicInfo.getModelUrl() + "\\", fileName));
            }
            ImageIO.write(image, "jpg", outputStream);
            QRCodeRecord qrCodeRecord = new QRCodeRecord();
            qrCodeRecord.setTempleCode(qrCodeVo.getTypeCode().getCode());
            qrCodeRecord.setBusinessCode(qrCodeVo.getBusinessCode());
            qrCodeRecord.setFileName(fileName);
            qrCodeRecord.setSaveTime(LSHDateUtils.date2String(new Date(), LSHDateUtils.YYYY_MM_DD_HH_MM_SS1));
            if (!qrCodeVo.getBusinessCode().equals("00000000")) {
                qrCodeRecord.setUrl(qrcodePath + "\\" + fileName);
            } else {
                qrCodeRecord.setUrl(projectBasicInfo.getModelUrl() + "\\" + fileName);
            }
            qrCodeRecord.setMoney(qrCodeVo.getTypeCode().getMoney());
            qrCodeRecordMapper.create(qrCodeRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 加载图片素材
     *
     * @param ifOnly
     * @param typeCode
     */
    public void loadImage(boolean ifOnly, boolean ifShowLogo, String typeCode, String bussinessCode) {
        try {
            //加载码眼
            image_eye = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\eye.png"));
            //装载50*50的不加内容的素材
            image01 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\01.png"));
            image02 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\02.png"));
            image03 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\03.png"));
            //装载50*50的图片素材
            image11 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\11.png"));
            image12 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\12.png"));
            image13 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\13.png"));
            //装载100*50的图片素材
            image21 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\21.png"));
            image22 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\22.png"));
            image23 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\23.png"));
            //装载50*100的图片素材
            image31 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\31.png"));
            image32 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\32.png"));
            image33 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\33.png"));
            //装载100*100的图片素材
            image41 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\41.png"));
            image42 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\42.png"));
            image43 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\43.png"));
            if (ifShowLogo) {
                imageLogo = ImageIO.read(new FileInputStream(projectBasicInfo.getBusinessUrl() + "\\" + bussinessCode + "\\logo.jpg"));
                imageLogoBorder = ImageIO.read(new FileInputStream(projectBasicInfo.getBusinessUrl() + "\\logo_border.png"));
            }
            if (!ifOnly) {
                imageBG = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\bg.jpg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 绘制 算法0  热门二维码   素材有50*50 50*100 100*50 100*100
     *
     * @param gs   画笔
     * @param code 二维码数组
     */
    public void drawQrcodeHot(Graphics2D gs, boolean[][] code) {
        //把图片素材放进数组
        BufferedImage[] img0 = {image01, image02, image03};
        BufferedImage[] img1 = {image11, image12, image13};
        BufferedImage[] img2 = {image21, image22, image23};
        BufferedImage[] img3 = {image31, image32, image33};
        BufferedImage[] img4 = {image41, image42, image43};

        //通用地绘制码眼
        gs.drawImage(image_eye, pix, pix, pix * 7, pix * 7, null);
        gs.drawImage(image_eye, (codeLength - 7) * pix + pixoff, pix, pix * 7, pix * 7, null);
        gs.drawImage(image_eye, pix, (codeLength - 7) * pix + pixoff, pix * 7, pix * 7, null);

        Random random = new Random();
        Set<String> set = new HashSet<>();

        // 绘制
        for (int i = -1; i < codeLength + 1; i++) {
            for (int j = -1; j < codeLength + 1; j++) {
                if (i == -1 || j == -1 || i == codeLength || j == codeLength) {
                    //随机取图片，画50*50的图
                    int s0 = random.nextInt(max);
                    gs.drawImage(img0[s0], i * pix + pixoff, j * pix + pixoff, pix, pix, null);
                    continue;
                }
                if (code[i][j]) {
                    if (i + 1 < codeLength && j + 1 < codeLength && code[i][j + 1] && code[i + 1][j + 1] && code[i + 1][j]) {
                        //随机取图片，画100*100的图
                        int s4 = random.nextInt(max);
                        gs.drawImage(img4[s4], i * pix + pixoff, j * pix + pixoff, 2 * pix, 2 * pix, null);
                        code[i][j + 1] = code[i + 1][j] = code[i + 1][j + 1] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        String code2 = String.valueOf(i) + ":" + String.valueOf(j + 1);
                        String code3 = String.valueOf(i + 1) + ":" + String.valueOf(j + 1);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                        if (!set.contains(code2)) {
                            set.add(code2);
                        }
                        if (!set.contains(code3)) {
                            set.add(code3);
                        }
                    } else if (j + 1 < codeLength && code[i][j + 1]) {
                        //随机取图片，画50*100的图
                        int s3 = random.nextInt(max);
                        gs.drawImage(img3[s3], i * pix + pixoff, j * pix + pixoff, pix, 2 * pix, null);
                        code[i][j + 1] = false;
                        String code1 = String.valueOf(i) + ":" + String.valueOf(j + 1);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                    } else if (i + 1 < codeLength && code[i + 1][j]) {
                        //随机取图片，画100*50的图
                        int s2 = random.nextInt(max);
                        gs.drawImage(img2[s2], i * pix + pixoff, j * pix + pixoff, 2 * pix, pix, null);
                        code[i + 1][j] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                    } else {
                        //随机取图片，画50*50的图
                        int s1 = random.nextInt(max);
                        gs.drawImage(img1[s1], i * pix + pixoff, j * pix + pixoff, pix, pix, null);
                    }
                } else {
                    String code1 = String.valueOf(i) + ":" + String.valueOf(j);
                    if (set.contains(code1) || i < 7 && j < 7 || i < 7 && j >= codeLength - 7 || j < 7 && i >= codeLength - 7) {
                        continue;
                    }
                    //随机取图片，画50*50的图
                    int s0 = random.nextInt(max);
                    gs.drawImage(img0[s0], i * pix + pixoff, j * pix + pixoff, pix, pix, null);
                }
            }
        }
    }

    /**
     * 绘制  算法1 普通二维码   素材有50*50 50*100 50*150
     *
     * @param gs   画笔
     * @param code 二维码数组
     */
    public void drawQrcodeOrdi(Graphics2D gs, boolean[][] code) {
        //把图片素材放进数组
        BufferedImage[] img1 = {image11, image12, image13};
        BufferedImage[] img2 = {image21, image22, image23};
        BufferedImage[] img3 = {image31, image32, image33};

        //通用地绘制码眼
        gs.drawImage(image_eye, pix, pix, pix * 7, pix * 7, null);
        gs.drawImage(image_eye, (codeLength - 7) * pix + pixoff, pix, pix * 7, pix * 7, null);
        gs.drawImage(image_eye, pix, (codeLength - 7) * pix + pixoff, pix * 7, pix * 7, null);

        Random random = new Random();

        for (int i = 0; i < codeLength; i++) {
            for (int j = 0; j < codeLength; j++) {
                //1*3
                if (code[i][j]) {
                    if (i + 2 < codeLength && code[i + 1][j] && code[i + 2][j]) {
                        //随机取图片  下标随机[0,2]，画50*150的图
                        int s3 = random.nextInt(max);
                        gs.drawImage(img3[s3], j * 25 + pixoff, i * 25 + pixoff, 25, 75, null);
                        code[i + 2][j] = false;
                        code[i + 1][j] = false;
                    } else if (i + 1 < codeLength && code[i + 1][j]) {
                        //1*2
                        //随机取图片，画50*100的图
                        int s2 = random.nextInt(max);
                        gs.drawImage(img2[s2], j * 25 + pixoff, i * 25 + pixoff, 25, 50, null);
                        code[i + 1][j] = false;
                    } else {
                        //随机取图片，画50*50的图
                        int s1 = random.nextInt(max);
                        gs.drawImage(img1[s1], j * 25 + pixoff, i * 25 + pixoff, 25, 25, null);
                    }
                }
            }
        }
    }

}