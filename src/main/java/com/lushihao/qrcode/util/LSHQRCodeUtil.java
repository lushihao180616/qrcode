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
import java.io.*;
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

    // 二维码宽度
    private int width = 1950;
    // 二维码高度
    private int height = 1950;
    // 设置偏移量，不设置可能导致解析出错
    private int pixoff = 50;
    // 像素大小
    private int pix = 50;
    // logo背景宽度
    private int logoBgWidth = 240;
    // logo背景高度
    private int logoBgHeight = 240;
    // logo宽度
    private int logoWidth = 200;
    // logo高度
    private int logoHeight = 200;
    // 二维码数组的长度
    private int codeLength;
    // 随机数，生成[0,2]之间的随机整数,取长度为3的数组下标
    private int max = 1;
    // 自定义背景图片较短边长度
    private int bgMinWidthOrHeight = 4800;

    //素材图片容器
    private BufferedImage image_eye;
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
        max = qrCodeVo.getTypeCode().getIconNum();
        FileOutputStream outputStream = null;

        try {
            //加载图片
            loadImage(qrCodeVo);
            //创建二维码
            BufferedImage image = getQRCode(qrCodeVo);
            //添加背景
            image = addBG(image, qrCodeVo);
            //输出图片
            String filePath = outPutImage(image, qrCodeVo);
            //记录一下
            qrCodeRecordMapper.create(new QRCodeRecord(qrCodeVo.getTypeCode().getCode(), qrCodeVo.getBusinessCode(), filePath.substring(filePath.lastIndexOf("\\") + 1), filePath, LSHDateUtils.date2String(new Date(), LSHDateUtils.YYYY_MM_DD_HH_MM_SS1), qrCodeVo.getTypeCode().getMoney()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 加载图片素材
     *
     * @param qrCodeVo
     */
    private void loadImage(QRCodeVo qrCodeVo) throws IOException {
        String typeCode = qrCodeVo.getTypeCode().getCode();
        //加载码眼
        image_eye = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\eye.png"));

    }

    /**
     * 创建二维码
     *
     * @param qrCodeVo
     */
    private BufferedImage getQRCode(QRCodeVo qrCodeVo) throws IOException {
        //创建二维码对象
        Qrcode qrcode = new Qrcode();
        //设置二维码的纠错级别
        //L(7%) M(15%) Q(25%) H(30%)
        qrcode.setQrcodeErrorCorrect('L'); //一般纠错级别小一点
        //设置二维码的编码模式 Binary(按照字节编码模式)
        qrcode.setQrcodeEncodeMode('B');
        //设置二维码的版本号 1-40  1:20*21    2:25*25   40:177*177
        qrcode.setQrcodeVersion(5);

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

        //处理码眼部分
        handleCodeEye(gs, code);
        //绘制二维码，选择算法
        if (qrCodeVo.getTypeCode().getArti().equals("0-1-2-3-4")) {
            drawQrcodeHot(gs, code, qrCodeVo); //0-1-2-3-4
        } else if (qrCodeVo.getTypeCode().getArti().equals("0-1-2-5-6")) {
            drawQrcodeOrdi(gs, code, qrCodeVo); //0-1-2-5-6
        }

        //添加logo
        if (qrCodeVo.getTypeCode().isIfShowLogo()) {
            imageLogo = ImageIO.read(new FileInputStream(projectBasicInfo.getBusinessUrl() + "\\" + qrCodeVo.getBusinessCode() + "\\logo.jpg"));
            imageLogoBorder = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getTypeCode().getCode() + "\\logo_border.png"));

            gs.drawImage(imageLogo, (width - logoWidth) / 2, (height - logoHeight) / 2, logoWidth, logoHeight, null);
            gs.drawImage(imageLogoBorder, (width - logoBgWidth) / 2, (height - logoBgHeight) / 2, logoBgWidth, logoBgHeight, null);
        }
        //释放画笔
        gs.dispose();
        return image;
    }

    /**
     * 绘制码眼
     *
     * @param gs
     * @param code
     */
    private void handleCodeEye(Graphics2D gs, boolean[][] code) {
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

        //通用地绘制码眼
        gs.drawImage(image_eye, pix, pix, pix * 7, pix * 7, null);
        gs.drawImage(image_eye, (codeLength - 7) * pix + pixoff, pix, pix * 7, pix * 7, null);
        gs.drawImage(image_eye, pix, (codeLength - 7) * pix + pixoff, pix * 7, pix * 7, null);
    }

    /**
     * 添加背景图片
     *
     * @param image
     * @param qrCodeVo
     * @return
     */
    private BufferedImage addBG(BufferedImage image, QRCodeVo qrCodeVo) throws IOException {
        if (!qrCodeVo.getTypeCode().isIfOnly()) {
            if (qrCodeVo.getTypeCode().isIfSelfBg() && !qrCodeVo.getBusinessCode().equals("00000000")) {
                imageBG = ImageIO.read(new FileInputStream(qrCodeVo.getBackGround()));
            } else {
                imageBG = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getTypeCode().getCode() + "\\bg.jpg"));
            }
        }
        if (!qrCodeVo.getTypeCode().isIfOnly()) {
            //获取图片缓存流对象
            BufferedImage backGroundImage = new BufferedImage(qrCodeVo.getTypeCode().getWidth(), qrCodeVo.getTypeCode().getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D bg = backGroundImage.createGraphics();
            int bgWidth;
            int bgHeight;
            if (qrCodeVo.getTypeCode().isIfSelfBg() && !qrCodeVo.getBusinessCode().equals("00000000")) {//自定义图片处理方式
                int width = backGroundImage.getWidth();
                int height = backGroundImage.getHeight();
                if (width > height) {
                    bgWidth = bgMinWidthOrHeight;
                    bgHeight = bgMinWidthOrHeight * height / width;
                } else {
                    bgWidth = bgMinWidthOrHeight * width / height;
                    bgHeight = bgMinWidthOrHeight;
                }
            } else {//非自定义图片处理方式
                bgWidth = qrCodeVo.getTypeCode().getWidth();
                bgHeight = qrCodeVo.getTypeCode().getHeight();
            }
            bg.drawImage(imageBG, 0, 0, bgWidth, bgHeight, null);
            bg.drawImage(image, qrCodeVo.getTypeCode().getX(), qrCodeVo.getTypeCode().getY(), width, height, null);
            bg.dispose();
            return backGroundImage;
        }
        return image;
    }

    /**
     * 创建文件夹
     *
     * @param qrcodePath
     * @return
     */
    private String createPath(String qrcodePath) {
        File qrcodeDirectory = new File(qrcodePath);
        if (!qrcodeDirectory.exists()) {//如果文件夹不存在
            qrcodeDirectory.mkdir();//创建文件夹
        }
        return qrcodePath;
    }


    /**
     * 绘制 算法0  热门二维码   素材有50*50 50*100 100*50 100*100
     *
     * @param gs   画笔
     * @param code 二维码数组
     */
    private void drawQrcodeHot(Graphics2D gs, boolean[][] code, QRCodeVo qrCodeVo) throws IOException {
        String typeCode = qrCodeVo.getTypeCode().getCode();

        //把图片素材放进数组
        BufferedImage[] img0 = new BufferedImage[max];
        BufferedImage[] img1 = new BufferedImage[max];
        BufferedImage[] img2 = new BufferedImage[max];
        BufferedImage[] img3 = new BufferedImage[max];
        BufferedImage[] img4 = new BufferedImage[max];
        for (int i = 1; i <= max; i++) {
            BufferedImage image0 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\0" + i + ".png"));
            BufferedImage image1 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\1" + i + ".png"));
            BufferedImage image2 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\2" + i + ".png"));
            BufferedImage image3 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\3" + i + ".png"));
            BufferedImage image4 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\4" + i + ".png"));
            img0[i - 1] = image0;
            img1[i - 1] = image1;
            img2[i - 1] = image2;
            img3[i - 1] = image3;
            img4[i - 1] = image4;
        }

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
    private void drawQrcodeOrdi(Graphics2D gs, boolean[][] code, QRCodeVo qrCodeVo) throws IOException {
        String typeCode = qrCodeVo.getTypeCode().getCode();
        //把图片素材放进数组
        BufferedImage[] img0 = new BufferedImage[max];
        BufferedImage[] img1 = new BufferedImage[max];
        BufferedImage[] img2 = new BufferedImage[max];
        BufferedImage[] img5 = new BufferedImage[max];
        BufferedImage[] img6 = new BufferedImage[max];
        for (int i = 1; i <= max; i++) {
            BufferedImage image0 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\0" + i + ".png"));
            BufferedImage image1 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\1" + i + ".png"));
            BufferedImage image2 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\2" + i + ".png"));
            BufferedImage image5 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\5" + i + ".png"));
            BufferedImage image6 = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\6" + i + ".png"));
            img0[i - 1] = image0;
            img1[i - 1] = image1;
            img2[i - 1] = image2;
            img5[i - 1] = image5;
            img6[i - 1] = image6;
        }

        Random random = new Random();
        Set<String> set = new HashSet<>();

        for (int i = 0; i < codeLength; i++) {
            for (int j = 0; j < codeLength; j++) {
                int iconIndex = random.nextInt(max);
                if (i == -1 || j == -1 || i == codeLength || j == codeLength) {
                    //随机取图片，画50*50的图
                    gs.drawImage(img0[iconIndex], i * pix + pixoff, j * pix + pixoff, pix, pix, null);
                    continue;
                }
                if (code[i][j]) {
                    if (i + 3 < codeLength && code[i + 1][j] && code[i + 2][j] && code[i + 3][j]) {
                        //随机取图片，画200*50的图
                        gs.drawImage(img6[iconIndex], i * pix + pixoff, j * pix + pixoff, 4 * pix, pix, null);
                        code[i + 1][j] = code[i + 2][j] = code[i + 3][j] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        String code2 = String.valueOf(i + 2) + ":" + String.valueOf(j);
                        String code3 = String.valueOf(i + 3) + ":" + String.valueOf(j);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                        if (!set.contains(code2)) {
                            set.add(code2);
                        }
                        if (!set.contains(code3)) {
                            set.add(code3);
                        }
                    } else if (i + 2 < codeLength && code[i + 1][j] && code[i + 2][j]) {
                        //随机取图片，画150*50的图
                        gs.drawImage(img5[iconIndex], i * pix + pixoff, j * pix + pixoff, 3 * pix, pix, null);
                        code[i + 1][j] = code[i + 2][j] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        String code2 = String.valueOf(i + 2) + ":" + String.valueOf(j);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                        if (!set.contains(code2)) {
                            set.add(code2);
                        }
                    } else if (i + 1 < codeLength && code[i + 1][j]) {
                        //随机取图片，画100*50的图
                        gs.drawImage(img2[iconIndex], i * pix + pixoff, j * pix + pixoff, 2 * pix, pix, null);
                        code[i + 1][j] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                    } else {
                        //随机取图片，画50*50的图
                        gs.drawImage(img1[iconIndex], i * pix + pixoff, j * pix + pixoff, pix, pix, null);
                    }
                } else {
                    String code1 = String.valueOf(i) + ":" + String.valueOf(j);
                    if (set.contains(code1) || i < 7 && j < 7 || i < 7 && j >= codeLength - 7 || j < 7 && i >= codeLength - 7) {
                        continue;
                    }
                    //随机取图片，画50*50的图
                    gs.drawImage(img0[iconIndex], i * pix + pixoff, j * pix + pixoff, pix, pix, null);
                }
            }
        }
    }

    /**
     * 输出图片
     *
     * @param qrCodeVo
     */
    private String outPutImage(BufferedImage image, QRCodeVo qrCodeVo) throws IOException {
        String filePath;
        if (!qrCodeVo.getBusinessCode().equals("00000000")) {//商家创建
            filePath = createPath(projectBasicInfo.getQrcodeUrl() + "\\" + qrCodeVo.getBusinessCode() + "\\" + new SimpleDateFormat("yyyy_MM_dd").format(new Date())) + "\\" + new SimpleDateFormat("HH_mm_ss_").format(new Date()) + qrCodeVo.getFileName() + ".jpg";
        } else {//操作员创建
            filePath = projectBasicInfo.getModelUrl() + "\\" + qrCodeVo.getFileName() + ".jpg";
        }
        //将文件输出
        ImageIO.write(image, "jpg", new FileOutputStream(new File(filePath)));
        return filePath;
    }

}