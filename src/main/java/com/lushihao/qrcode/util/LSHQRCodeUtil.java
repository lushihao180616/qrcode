package com.lushihao.qrcode.util;

import com.lushihao.myutils.time.LSHDateUtils;
import com.lushihao.qrcode.dao.QRCodeRecordMapper;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeVo;
import com.swetake.util.Qrcode;
import org.jim2mov.core.MovieSaveException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * QrcodeText 二维码
 *
 * @author lushihao
 */
@Component
public class LSHQRCodeUtil {

    @Resource
    private ProjectBasicInfo projectBasicInfo;
    @Resource
    private QRCodeRecordMapper qrCodeRecordMapper;
    @Resource
    private LSHGifUtil lshGif2JpgUtil;

    // 二维码宽度
    private int width = 1950;
    // 二维码高度
    private int height = 1950;
    // 二维码背景宽度
    private int defaultWidth = 3000;
    // 二维码背景高度
    private int defaultHeight = 3000;
    // 当前二维码宽度
    int nowWidth = 3000;
    // 当前二维码高度
    int nowHeight = 3000;
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
    // 是否动态背景
    private boolean isMp4 = false;
    // 缩放倍数
    private int multiple = 1;

    /**
     * 生成二维码
     *
     * @param qrCodeVo 二维码相关信息
     * @return
     */
    public Map<String, String> qrcode(QRCodeVo qrCodeVo, boolean ifTest) {
        max = qrCodeVo.getQrCodeTemple().getIconNum();
        multiple = qrCodeVo.getQrCodeTemple().getMultiple();
        FileOutputStream outputStream = null;

        Map<String, String> map = new HashMap<>();
        try {
            //创建二维码
            BufferedImage image = getQRCode(qrCodeVo, ifTest);
            if (image == null) {
                map.put("result", "信息过长，创建失败");
                return map;
            }
            //添加背景
            Map<Integer, BufferedImage> imageAndBg = addBG(image, qrCodeVo, ifTest);
            //输出图片
            String filePath = outPutImage(imageAndBg, qrCodeVo, ifTest);
            map.put("filePath", filePath);
            //记录一下
            if (!ifTest) {
                qrCodeRecordMapper.create(new QRCodeRecord(qrCodeVo.getQrCodeTemple().getCode(), qrCodeVo.getBusinessCode(), filePath.substring(filePath.lastIndexOf("\\") + 1), filePath, LSHDateUtils.date2String(new Date(), LSHDateUtils.YYYY_MM_DD_HH_MM_SS1), qrCodeVo.getQrCodeTemple().getMoney()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "创建失败，请检查您的输入条件");
            return map;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put("result", "创建成功");
        return map;
    }

    /**
     * 创建二维码
     *
     * @param qrCodeVo
     */
    private BufferedImage getQRCode(QRCodeVo qrCodeVo, boolean ifTest) throws IOException {
        BufferedImage image;
        if (ifTest) {//预览的情况
            image = new BufferedImage(3000, 3000, BufferedImage.TYPE_INT_RGB);
            //获取画笔
            Graphics2D bgG2 = image.createGraphics();
            //设置透明
            image = bgG2.getDeviceConfiguration().createCompatibleImage(nowWidth, nowHeight, Transparency.TRANSLUCENT);
            bgG2 = image.createGraphics();

            BufferedImage qrcodeImage = new BufferedImage(1850, 1850, BufferedImage.TYPE_INT_RGB);
            Graphics2D qrcodeG2 = qrcodeImage.createGraphics();
            qrcodeG2.setBackground(Color.BLACK);
            qrcodeG2.clearRect(0, 0, 1850, 1850);
            Font font = new Font("微软雅黑", Font.PLAIN, 200);
            qrcodeG2.setFont(font);              //设置字体
            qrcodeG2.setColor(Color.WHITE); //根据图片的背景设置水印颜色
            qrcodeG2.drawString("二维码", 625, 1000);
            qrcodeG2.dispose();

            bgG2.drawImage(qrcodeImage, 575, 575, 1850, 1850, null);
            bgG2.dispose();
        } else {
            //创建二维码对象
            Qrcode qrcode = new Qrcode();
            //设置二维码的纠错级别
            //L(7%) M(15%) Q(25%) H(30%)
            qrcode.setQrcodeErrorCorrect('L'); //一般纠错级别小一点
            //设置二维码的编码模式 Binary(按照字节编码模式)
            qrcode.setQrcodeEncodeMode('B');

            //设置内容
            String content = qrCodeVo.getMessage();
            byte[] contentsBytes = content.getBytes("utf-8");
            boolean[][] code = new boolean[0][];
            try {
                //设置二维码的版本号 1-40  1:20*21    2:25*25   40:177*177
                qrcode.setQrcodeVersion(5);
                //二维码
                code = qrcode.calQrcode(contentsBytes);
                width = 1950;
                height = 1950;
                nowWidth = 3000;
                nowHeight = 3000;
            } catch (ArrayIndexOutOfBoundsException e) {
                try {
                    //设置二维码的版本号 1-40  1:20*21    2:25*25   40:177*177
                    qrcode.setQrcodeVersion(10);
                    //二维码
                    code = qrcode.calQrcode(contentsBytes);
                    width = 2950;
                    height = 2950;
                    nowWidth = 4538;
                    nowHeight = 4538;
                } catch (ArrayIndexOutOfBoundsException c) {
                    System.out.println("特长文字");
                }
            }
            if (code.length == 0) {
                return null;
            }
            //获取二维码数组的长度
            codeLength = code.length;

            image = new BufferedImage(nowWidth, nowHeight, BufferedImage.TYPE_INT_RGB);
            //获取画笔
            Graphics2D gs = image.createGraphics();
            //设置透明
            image = gs.getDeviceConfiguration().createCompatibleImage(nowWidth, nowHeight, Transparency.TRANSLUCENT);
            gs = image.createGraphics();

            gs.translate(nowWidth / 2, nowHeight / 2);
            gs.rotate(Math.toRadians(qrCodeVo.getQrCodeTemple().getAngle()));
            gs.translate(-nowWidth / 2, -nowHeight / 2);
            //处理码眼部分
            handleCodeEye(gs, code, qrCodeVo);
            //绘制二维码，选择算法
            if (qrCodeVo.getQrCodeTemple().getArti().equals("0-1-2-3-4")) {
                drawQrcodeHot(gs, code, qrCodeVo); //0-1-2-3-4
            } else if (qrCodeVo.getQrCodeTemple().getArti().equals("0-1-2-5-6")) {
                drawQrcodeOrdi(gs, code, qrCodeVo); //0-1-2-5-6
            }

            //添加logo
            if (qrCodeVo.getQrCodeTemple().isIfShowLogo()) {
                BufferedImage imageLogo = ImageIO.read(new FileInputStream(projectBasicInfo.getBusinessUrl() + "\\" + qrCodeVo.getBusinessCode() + "\\logo.png"));
                BufferedImage imageLogoBorder = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getQrCodeTemple().getCode() + "\\logo_border.png"));

                gs.translate(nowWidth / 2, nowHeight / 2);
                gs.rotate(Math.toRadians(-qrCodeVo.getQrCodeTemple().getAngle()));
                gs.translate(-nowWidth / 2, -nowHeight / 2);

                gs.drawImage(imageLogo, (nowWidth - logoWidth) / 2, (nowHeight - logoHeight) / 2, logoWidth, logoHeight, null);
                gs.drawImage(imageLogoBorder, (nowWidth - logoBgWidth) / 2, (nowHeight - logoBgHeight) / 2, logoBgWidth, logoBgHeight, null);
            }
            //释放画笔
            gs.dispose();
        }
        return image;
    }

    /**
     * 绘制码眼
     *
     * @param gs
     * @param code
     */
    private void handleCodeEye(Graphics2D gs, boolean[][] code, QRCodeVo qrCodeVo) throws IOException {
        //加载码眼
        BufferedImage image_eye = ImageIO.read(new FileInputStream(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getQrCodeTemple().getCode() + "\\eye.png"));

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
        gs.drawImage(image_eye, (nowWidth - width) / 2 + pix, (nowHeight - height) / 2 + pix, pix * 7, pix * 7, null);
        gs.drawImage(image_eye, (nowWidth - width) / 2 + (codeLength - 7) * pix + pixoff, (nowHeight - height) / 2 + pix, pix * 7, pix * 7, null);
        gs.drawImage(image_eye, (nowWidth - width) / 2 + pix, (nowHeight - height) / 2 + (codeLength - 7) * pix + pixoff, pix * 7, pix * 7, null);
    }

    /**
     * 添加背景图片
     *
     * @param image
     * @param qrCodeVo
     * @return
     */
    private Map<Integer, BufferedImage> addBG(BufferedImage image, QRCodeVo qrCodeVo, boolean ifTest) throws IOException {
        BufferedImage nowImage = image;

        Map<Integer, BufferedImage> map = new HashMap<>();
        BufferedImage imageBG = null;
        if (!qrCodeVo.getQrCodeTemple().isIfOnly()) {//处理有背景
            int bgWidth;
            int bgHeight;
            if (qrCodeVo.getQrCodeTemple().isIfSelfBg() && !qrCodeVo.getBusinessCode().equals("00000000")) {
                imageBG = ImageIO.read(new FileInputStream(qrCodeVo.getBackGround()));
                int width = imageBG.getWidth();
                int height = imageBG.getHeight();
                if (width > height) {
                    bgHeight = qrCodeVo.getShortLength();
                    bgWidth = (int) ((float) width / height * bgHeight);
                } else {
                    bgWidth = qrCodeVo.getShortLength();
                    bgHeight = (int) ((float) height / width * bgWidth);
                }
                isMp4 = false;
            } else {
                File file = new File(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getQrCodeTemple().getCode() + "\\bg.jpg");
                if (file.exists()) {//bg.jpg
                    isMp4 = false;
                    imageBG = ImageIO.read(new FileInputStream(file));
                } else {
                    isMp4 = true;
                    map = lshGif2JpgUtil.gifToJpg(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getQrCodeTemple().getCode() + "\\bg.gif");
                }
                bgWidth = qrCodeVo.getQrCodeTemple().getWidth();
                bgHeight = qrCodeVo.getQrCodeTemple().getHeight();
            }

            if (isMp4) {//动图背景
                for (int i = 0; i < map.size(); i++) {
                    //获取图片缓存流对象
                    BufferedImage backGroundImage = new BufferedImage(bgWidth / multiple, bgHeight / multiple, BufferedImage.TYPE_INT_RGB);
                    Graphics2D bg = backGroundImage.createGraphics();
                    bg.drawImage(map.get(i), 0, 0, bgWidth / multiple, bgHeight / multiple, null);
                    if (qrCodeVo.getQrCodeTemple().getStartQRFrame() == 0 && qrCodeVo.getQrCodeTemple().getEndQRFrame() == 0) {
                        if (ifTest) {
                            bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.5));
                            bg.drawImage(nowImage, (qrCodeVo.getQrCodeTemple().getX() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, (qrCodeVo.getQrCodeTemple().getY() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
                            bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                        } else {
                            bg.drawImage(nowImage, (qrCodeVo.getQrCodeTemple().getX() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, (qrCodeVo.getQrCodeTemple().getY() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
                        }
                    } else if (i >= qrCodeVo.getQrCodeTemple().getStartQRFrame() && i <= qrCodeVo.getQrCodeTemple().getEndQRFrame()) {
                        if (ifTest) {
                            bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.5));
                            bg.drawImage(nowImage, (qrCodeVo.getQrCodeTemple().getX() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, (qrCodeVo.getQrCodeTemple().getY() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
                            bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                        } else {
                            bg.drawImage(nowImage, (qrCodeVo.getQrCodeTemple().getX() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, (qrCodeVo.getQrCodeTemple().getY() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
                        }
                    }
                    bg.dispose();
                    map.put(i, backGroundImage);
                }
                return map;
            } else {//静态背景
                //获取图片缓存流对象
                BufferedImage backGroundImage = new BufferedImage(bgWidth / multiple, bgHeight / multiple, BufferedImage.TYPE_INT_RGB);
                Graphics2D bg = backGroundImage.createGraphics();
                bg.drawImage(imageBG, 0, 0, bgWidth / multiple, bgHeight / multiple, null);
                if (ifTest) {
                    bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.5));
                    bg.drawImage(nowImage, (qrCodeVo.getQrCodeTemple().getX() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, (qrCodeVo.getQrCodeTemple().getY() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
                    bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                } else {
                    bg.drawImage(nowImage, (qrCodeVo.getQrCodeTemple().getX() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, (qrCodeVo.getQrCodeTemple().getY() + (bgWidth - 1950) * qrCodeVo.getX() / 100) / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
                }
                bg.dispose();
                map.put(0, backGroundImage);
                return map;
            }
        } else {//处理单码
            //获取图片缓存流对象
            BufferedImage backGroundImage = new BufferedImage(1950 / multiple, 1950 / multiple, BufferedImage.TYPE_INT_RGB);
            Graphics2D bg = backGroundImage.createGraphics();
            bg.setBackground(Color.WHITE);
            bg.clearRect(0, 0, 1950 / multiple, 1950 / multiple);
            if (ifTest) {
                bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (float) 0.5));
                bg.drawImage(nowImage, qrCodeVo.getQrCodeTemple().getX() / multiple, qrCodeVo.getQrCodeTemple().getY() / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
                bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            } else {
                bg.drawImage(nowImage, qrCodeVo.getQrCodeTemple().getX() / multiple, qrCodeVo.getQrCodeTemple().getY() / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
            }
            bg.dispose();
            map.put(0, backGroundImage);
            isMp4 = false;
            return map;
        }
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
        String typeCode = qrCodeVo.getQrCodeTemple().getCode();

        //把图片素材放进数组
        BufferedImage[] img0 = new BufferedImage[max];
        BufferedImage[] img1 = new BufferedImage[max];
        BufferedImage[] img2 = new BufferedImage[max];
        BufferedImage[] img3 = new BufferedImage[max];
        BufferedImage[] img4 = new BufferedImage[max];
        for (int i = 1; i <= max; i++) {
            File file0 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\0" + i + ".png");
            if (file0.exists()) {
                BufferedImage image0 = ImageIO.read(new FileInputStream(file0));
                img0[i - 1] = image0;
            }
            File file1 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\1" + i + ".png");
            if (file1.exists()) {
                BufferedImage image1 = ImageIO.read(new FileInputStream(file1));
                img1[i - 1] = image1;
            }
            File file2 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\2" + i + ".png");
            if (file2.exists()) {
                BufferedImage image2 = ImageIO.read(new FileInputStream(file2));
                img2[i - 1] = image2;
            }
            File file3 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\3" + i + ".png");
            if (file3.exists()) {
                BufferedImage image3 = ImageIO.read(new FileInputStream(file3));
                img3[i - 1] = image3;
            }
            File file4 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\4" + i + ".png");
            if (file4.exists()) {
                BufferedImage image4 = ImageIO.read(new FileInputStream(file4));
                img4[i - 1] = image4;
            }
        }

        Random random = new Random();
        Set<String> set = new HashSet<>();

        // 绘制
        for (int i = -1; i < codeLength + 1; i++) {
            for (int j = -1; j < codeLength + 1; j++) {
                if (i == -1 || j == -1 || i == codeLength || j == codeLength) {
                    //随机取图片，画50*50的图
                    int s0 = random.nextInt(max);
                    if (img0[0] != null) {
//                        gs.drawImage(img0[s0], (defaultWidth - width) / 2 + i * pix + pixoff, (defaultHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
                    continue;
                }
                if (code[i][j]) {
                    if (img4[0] != null && i + 1 < codeLength && j + 1 < codeLength && code[i][j + 1] && code[i + 1][j + 1] && code[i + 1][j]) {
                        //随机取图片，画100*100的图
                        int s4 = random.nextInt(max);
                        gs.drawImage(img4[s4], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 2 * pix, 2 * pix, null);
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
                    } else if (img3[0] != null && j + 1 < codeLength && code[i][j + 1]) {
                        //随机取图片，画50*100的图
                        int s3 = random.nextInt(max);
                        gs.drawImage(img3[s3], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, pix, 2 * pix, null);
                        code[i][j + 1] = false;
                        String code1 = String.valueOf(i) + ":" + String.valueOf(j + 1);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                    } else if (img2[0] != null && i + 1 < codeLength && code[i + 1][j]) {
                        //随机取图片，画100*50的图
                        int s2 = random.nextInt(max);
                        gs.drawImage(img2[s2], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 2 * pix, pix, null);
                        code[i + 1][j] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                    } else {
                        //随机取图片，画50*50的图
                        int s1 = random.nextInt(max);
                        gs.drawImage(img1[s1], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
                } else {
                    String code1 = String.valueOf(i) + ":" + String.valueOf(j);
                    if (set.contains(code1) || i < 7 && j < 7 || i < 7 && j >= codeLength - 7 || j < 7 && i >= codeLength - 7) {
                        continue;
                    }
                    //随机取图片，画50*50的图
                    int s0 = random.nextInt(max);
                    if (img0[0] != null) {
                        gs.drawImage(img0[s0], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
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
        String typeCode = qrCodeVo.getQrCodeTemple().getCode();
        //把图片素材放进数组
        BufferedImage[] img0 = new BufferedImage[max];
        BufferedImage[] img1 = new BufferedImage[max];
        BufferedImage[] img2 = new BufferedImage[max];
        BufferedImage[] img5 = new BufferedImage[max];
        BufferedImage[] img6 = new BufferedImage[max];
        BufferedImage[] img7 = new BufferedImage[max];
        for (int i = 1; i <= max; i++) {
            File file0 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\0" + i + ".png");
            if (file0.exists()) {
                BufferedImage image0 = ImageIO.read(new FileInputStream(file0));
                img0[i - 1] = image0;
            }
            File file1 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\1" + i + ".png");
            if (file1.exists()) {
                BufferedImage image1 = ImageIO.read(new FileInputStream(file1));
                img1[i - 1] = image1;
            }
            File file2 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\2" + i + ".png");
            if (file2.exists()) {
                BufferedImage image2 = ImageIO.read(new FileInputStream(file2));
                img2[i - 1] = image2;
            }
            File file5 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\5" + i + ".png");
            if (file5.exists()) {
                BufferedImage image3 = ImageIO.read(new FileInputStream(file5));
                img5[i - 1] = image3;
            }
            File file6 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\6" + i + ".png");
            if (file6.exists()) {
                BufferedImage image4 = ImageIO.read(new FileInputStream(file6));
                img6[i - 1] = image4;
            }
            File file7 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\7" + i + ".png");
            if (file7.exists()) {
                BufferedImage image5 = ImageIO.read(new FileInputStream(file7));
                img7[i - 1] = image5;
            }
        }

        Random random = new Random();
        Set<String> set = new HashSet<>();

        for (int i = 0; i < codeLength; i++) {
            for (int j = 0; j < codeLength; j++) {
                int iconIndex = random.nextInt(max);
                if (i == -1 || j == -1 || i == codeLength || j == codeLength) {
                    //随机取图片，画50*50的图
                    if (img0[0] != null) {
                        //gs.drawImage(img0[iconIndex], (defaultWidth - width) / 2 + i * pix + pixoff, (defaultHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
                    continue;
                }
                if (code[i][j]) {
                    if (img7[0] != null && i + 4 < codeLength && code[i + 1][j] && code[i + 2][j] && code[i + 3][j] && code[i + 4][j]) {
                        //随机取图片，画200*50的图
                        gs.drawImage(img7[iconIndex], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 5 * pix, pix, null);
                        code[i + 1][j] = code[i + 2][j] = code[i + 3][j] = code[i + 4][j] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        String code2 = String.valueOf(i + 2) + ":" + String.valueOf(j);
                        String code3 = String.valueOf(i + 3) + ":" + String.valueOf(j);
                        String code4 = String.valueOf(i + 4) + ":" + String.valueOf(j);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                        if (!set.contains(code2)) {
                            set.add(code2);
                        }
                        if (!set.contains(code3)) {
                            set.add(code3);
                        }
                        if (!set.contains(code4)) {
                            set.add(code4);
                        }
                    } else if (img6[0] != null && i + 3 < codeLength && code[i + 1][j] && code[i + 2][j] && code[i + 3][j]) {
                        //随机取图片，画200*50的图
                        gs.drawImage(img6[iconIndex], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 4 * pix, pix, null);
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
                    } else if (img5[0] != null && i + 2 < codeLength && code[i + 1][j] && code[i + 2][j]) {
                        //随机取图片，画150*50的图
                        gs.drawImage(img5[iconIndex], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 3 * pix, pix, null);
                        code[i + 1][j] = code[i + 2][j] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        String code2 = String.valueOf(i + 2) + ":" + String.valueOf(j);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                        if (!set.contains(code2)) {
                            set.add(code2);
                        }
                    } else if (img2[0] != null && i + 1 < codeLength && code[i + 1][j]) {
                        //随机取图片，画100*50的图
                        gs.drawImage(img2[iconIndex], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 2 * pix, pix, null);
                        code[i + 1][j] = false;
                        String code1 = String.valueOf(i + 1) + ":" + String.valueOf(j);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                    } else {
                        //随机取图片，画50*50的图
                        gs.drawImage(img1[iconIndex], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
                } else {
                    String code1 = String.valueOf(i) + ":" + String.valueOf(j);
                    if (set.contains(code1) || i < 7 && j < 7 || i < 7 && j >= codeLength - 7 || j < 7 && i >= codeLength - 7) {
                        continue;
                    }
                    //随机取图片，画50*50的图
                    if (img0[0] != null) {
                        gs.drawImage(img0[iconIndex], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
                }
            }
        }
    }

    /**
     * 输出图片
     *
     * @param images
     * @param qrCodeVo
     */
    private String outPutImage(Map<Integer, BufferedImage> images, QRCodeVo qrCodeVo, boolean ifTest) throws IOException, MovieSaveException {
        String filePath;
        //将文件输出
        if (!qrCodeVo.getBusinessCode().equals("00000000")) {//商家创建
            if (ifTest) {
                filePath = projectBasicInfo.getQrcodeUrl() + "\\test\\" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_").format(new Date()) + qrCodeVo.getFileName() + "_test.jpg";
            } else {
                filePath = createPath(projectBasicInfo.getQrcodeUrl() + "\\" + qrCodeVo.getBusinessCode() + "\\" + new SimpleDateFormat("yyyy_MM_dd").format(new Date())) + "\\" + new SimpleDateFormat("HH_mm_ss_").format(new Date()) + qrCodeVo.getFileName() + ".jpg";
            }
        } else {//操作员创建
            filePath = projectBasicInfo.getModelUrl() + "\\" + qrCodeVo.getFileName() + ".jpg";
        }
        if (isMp4) {
            filePath = filePath.substring(0, filePath.lastIndexOf(".jpg")) + ".gif";
            new LSHGifUtil().jpgToGif(images, filePath, qrCodeVo.getQrCodeTemple().getFrame());
        } else {
            ImageIO.write(images.get(0), "jpg", new FileOutputStream(new File(filePath)));
        }
        return filePath;
    }

}