package com.lushihao.qrcode.util;

import com.lushihao.myutils.time.LSHDateUtils;
import com.lushihao.qrcode.dao.QRCodeRecordMapper;
import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.qrcode.QRCodeVo;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.swetake.util.Qrcode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
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
    @Resource
    private LSHImageUtil lshImageUtil;
    @Resource
    private LSHCharUtil lshCharUtil;

    /**
     * 二维码宽度
     */
    private int width = 1950;
    /**
     * 二维码高度
     */
    private int height = 1950;
    /**
     * 二维码背景宽度
     */
    private int defaultWidth = 3000;
    /**
     * 二维码背景高度
     */
    private int defaultHeight = 3000;
    /**
     * 当前二维码宽度
     */
    int nowWidth = 3000;
    /**
     * 当前二维码高度
     */
    int nowHeight = 3000;
    /**
     * 偏移量
     */
    private int pixoff = 50;
    /**
     * 每个点位长度宽度
     */
    private int pix = 50;
    /**
     * logo宽度
     */
    private int logoBgWidth = 240;
    /**
     * logo高度
     */
    private int logoBgHeight = 240;
    /**
     * logo图像宽度
     */
    private int logoWidth = 200;
    /**
     * logo图像高度
     */
    private int logoHeight = 200;
    /**
     * 二维码数组的长度
     */
    private int codeLength;
    /**
     * 子图数量
     */
    private int max = 1;
    /**
     * 是否动态背景
     */
    private boolean isMp4 = false;

    /**
     * 生成二维码
     *
     * @param qrCodeVo 二维码相关信息
     * @return
     */
    public Map<String, String> qrcode(QRCodeVo qrCodeVo, boolean ifTest, boolean ifModel) {
        //删掉测试记录
        if (!ifTest) {
            lshImageUtil.delDirFile(projectBasicInfo.getQrcodeUrl() + "\\test");
        }
        max = qrCodeVo.getQrCodeTemple().getIconNum();
        //判断是静态还是动态
        if (qrCodeVo.getQrCodeTemple().getCode().startsWith("J")) {
            isMp4 = false;
        } else if (qrCodeVo.getQrCodeTemple().getCode().startsWith("D")) {
            isMp4 = true;
        }

        Map<String, String> map = new HashMap<>();
        try {
            //创建二维码
            BufferedImage image = getQRCode(qrCodeVo, ifTest);
            if (image == null) {
                map.put("result", "信息过长，创建失败");
                return map;
            }
            //添加背景
            Map<Integer, BufferedImage> imageAndBg = addBG(image, qrCodeVo);
            //输出图片
            String filePath = outPutImage(imageAndBg, qrCodeVo, ifTest, ifModel);
            map.put("filePath", filePath);
            //记录一下
            if (!ifTest) {
                qrCodeRecordMapper.create(new QRCodeRecord(qrCodeVo.getQrCodeTemple().getCode(), qrCodeVo.getBusinessCode(), filePath.substring(filePath.lastIndexOf("\\") + 1), filePath, LSHDateUtils.date2String(new Date(), LSHDateUtils.YYYY_MM_DD_HH_MM_SS1), qrCodeVo.getQrCodeTemple().getMoney()));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            map.put("result", "创建失败，请检查您的输入条件");
            return map;
        }
        map.put("result", "创建成功");
        return map;
    }

    /**
     * 创建二维码
     *
     * @param qrCodeVo
     */
    private BufferedImage getQRCode(QRCodeVo qrCodeVo, boolean ifTest) throws UnsupportedEncodingException {
        //创建二维码对象
        Qrcode qrcode = new Qrcode();
        //设置二维码的纠错级别
        //L(7%) M(15%) Q(25%) H(30%)
        qrcode.setQrcodeErrorCorrect('L'); //一般纠错级别小一点
        //设置二维码的编码模式 Binary(按照字节编码模式)
        qrcode.setQrcodeEncodeMode('B');

        //设置内容
        String content = qrCodeVo.getMessage();
        content = qrCodeVo.getMessage();
        byte[] contentsBytes = content.getBytes("utf-8");
        int charLength = lshCharUtil.charLength(content);
        boolean[][] code = new boolean[0][];
        if (charLength <= 105) {
            qrcode.setQrcodeVersion(5);
            //二维码
            code = qrcode.calQrcode(contentsBytes);
            width = 1950;
            height = 1950;
            nowWidth = 3000;
            nowHeight = 3000;
        } else if (charLength <= 270) {
            qrcode.setQrcodeVersion(10);
            //二维码
            code = qrcode.calQrcode(contentsBytes);
            width = 2950;
            height = 2950;
            nowWidth = 4538;
            nowHeight = 4538;
        }
        if (code.length == 0) {
            return null;
        } else {
            codeLength = code.length;
        }

        //生成方框
        BufferedImage image = new BufferedImage(nowWidth, nowHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D gs = image.createGraphics();
        image = gs.getDeviceConfiguration().createCompatibleImage(nowWidth, nowHeight, Transparency.TRANSLUCENT);
        gs = image.createGraphics();
        gs.translate(nowWidth / 2, nowHeight / 2);
        if (qrCodeVo.getQrCodeTemple().isIfSelfBg()) {
            gs.rotate(Math.toRadians(qrCodeVo.getAngle()));
        } else {
            gs.rotate(Math.toRadians(qrCodeVo.getQrCodeTemple().getAngle()));
        }
        gs.translate(-nowWidth / 2, -nowHeight / 2);

        //开始回执二维码
        handleCodeEye(gs, code, qrCodeVo);
        if (qrCodeVo.getQrCodeTemple().getArti().equals("0-1-2-3-4")) {
            drawQrcodeHot(gs, code, qrCodeVo); //0-1-2-3-4
        } else if (qrCodeVo.getQrCodeTemple().getArti().equals("0-1-2-5-6")) {
            drawQrcodeOrdi(gs, code, qrCodeVo); //0-1-2-5-6
        }

        //添加logo
        if (qrCodeVo.getQrCodeTemple().isIfShowLogo()) {
            BufferedImage imageLogo = lshImageUtil.getImage(projectBasicInfo.getBusinessUrl() + "\\" + qrCodeVo.getBusinessCode() + "\\logo.png");
            BufferedImage imageLogoBorder = lshImageUtil.getImage(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getQrCodeTemple().getCode() + "\\logo_border.png");

            gs.translate(nowWidth / 2, nowHeight / 2);
            if (qrCodeVo.getQrCodeTemple().isIfSelfBg()) {
                gs.rotate(Math.toRadians(-qrCodeVo.getAngle()));
            } else {
                gs.rotate(Math.toRadians(-qrCodeVo.getQrCodeTemple().getAngle()));
            }
            gs.translate(-nowWidth / 2, -nowHeight / 2);

            gs.drawImage(imageLogo, (nowWidth - logoWidth) / 2, (nowHeight - logoHeight) / 2, logoWidth, logoHeight, null);
            gs.drawImage(imageLogoBorder, (nowWidth - logoBgWidth) / 2, (nowHeight - logoBgHeight) / 2, logoBgWidth, logoBgHeight, null);
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
    private void handleCodeEye(Graphics2D gs, boolean[][] code, QRCodeVo qrCodeVo) {
        BufferedImage image_eye = lshImageUtil.getImage(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getQrCodeTemple().getCode() + "\\eye.png");

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

        int x1 = (nowWidth - width) / 2 + pix;
        int x2 = (nowWidth - width) / 2 + (codeLength - 7) * pix + pixoff;
        int y1 = (nowHeight - height) / 2 + pix;
        int y2 = (nowHeight - height) / 2 + (codeLength - 7) * pix + pixoff;
        int eyeL = pix * 7;
        //通用地绘制码眼
        gs.drawImage(image_eye, x1, y1, eyeL, eyeL, null);
        gs.drawImage(image_eye, x2, y1, eyeL, eyeL, null);
        gs.drawImage(image_eye, x1, y2, eyeL, eyeL, null);
    }

    /**
     * 添加背景图片
     *
     * @param image
     * @param qrCodeVo
     * @return
     */
    private Map<Integer, BufferedImage> addBG(BufferedImage image, QRCodeVo qrCodeVo) {
        BufferedImage nowImage = image;

        Map<Integer, BufferedImage> map = new HashMap<>();
        int bgWidth;
        int bgHeight;
        int x;
        int y;
        float alpha = 1 - ((float) qrCodeVo.getAlpha() / 100);
        int multiple;

        if (qrCodeVo.getQrCodeTemple().isIfSelfBg()) {//自定义背景
            //缩小倍数
            multiple = 1;
            //获得背景图片
            if (!isMp4) {
                map.put(0, lshImageUtil.getImage(qrCodeVo.getBackGround()));
            } else {
                map = lshGif2JpgUtil.gifToJpg(qrCodeVo.getBackGround());
            }
            //获得背景宽高
            int width = map.get(0).getWidth();
            int height = map.get(0).getHeight();
            if (width > height) {
                bgHeight = qrCodeVo.getShortLength();
                bgWidth = (int) ((float) width / height * bgHeight);
            } else {
                bgWidth = qrCodeVo.getShortLength();
                bgHeight = (int) ((float) height / width * bgWidth);
            }
            //获得偏移量
            x = (int) ((float) (bgWidth - 3000) * qrCodeVo.getX() / 100);
            y = (int) ((float) (bgHeight - 3000) * qrCodeVo.getY() / 100);
        } else {//非自定义背景
            //缩小倍数
            multiple = qrCodeVo.getQrCodeTemple().getMultiple();
            //获得背景图片
            if (!isMp4) {
                map.put(0, lshImageUtil.getImage(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getQrCodeTemple().getCode() + "\\bg.jpg"));
            } else {
                map = lshGif2JpgUtil.gifToJpg(projectBasicInfo.getTempleUrl() + "\\" + qrCodeVo.getQrCodeTemple().getCode() + "\\bg.gif");
            }
            //获得背景宽高
            bgWidth = qrCodeVo.getQrCodeTemple().getWidth();
            bgHeight = qrCodeVo.getQrCodeTemple().getHeight();
            //获得偏移量
            x = qrCodeVo.getQrCodeTemple().getX();
            y = qrCodeVo.getQrCodeTemple().getY();
        }

        for (int i = 0; i < map.size(); i++) {
            //获取图片缓存流对象
            BufferedImage backGroundImage = new BufferedImage(bgWidth / multiple, bgHeight / multiple, BufferedImage.TYPE_INT_RGB);
            Graphics2D bg = backGroundImage.createGraphics();
            bg.drawImage(map.get(i), 0, 0, bgWidth / multiple, bgHeight / multiple, null);
            bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            bg.drawImage(nowImage, x / multiple, y / multiple, defaultWidth / multiple, defaultHeight / multiple, null);
            bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            bg.dispose();
            map.put(i, backGroundImage);
        }
        return map;
    }

    /**
     * 绘制 算法0  热门二维码   素材有50*50 50*100 100*50 100*100
     *
     * @param gs   画笔
     * @param code 二维码数组
     */
    private void drawQrcodeHot(Graphics2D gs, boolean[][] code, QRCodeVo qrCodeVo) {
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
                BufferedImage image0 = lshImageUtil.getImage(file0);
                img0[i - 1] = image0;
            }
            File file1 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\1" + i + ".png");
            if (file1.exists()) {
                BufferedImage image1 = lshImageUtil.getImage(file1);
                img1[i - 1] = image1;
            }
            File file2 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\2" + i + ".png");
            if (file2.exists()) {
                BufferedImage image2 = lshImageUtil.getImage(file2);
                img2[i - 1] = image2;
            }
            File file3 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\3" + i + ".png");
            if (file3.exists()) {
                BufferedImage image3 = lshImageUtil.getImage(file3);
                img3[i - 1] = image3;
            }
            File file4 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\4" + i + ".png");
            if (file4.exists()) {
                BufferedImage image4 = lshImageUtil.getImage(file4);
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
                        gs.drawImage(img0[s0], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
                    continue;
                }
                if (code[i][j]) {
                    if (img4[0] != null && i + 1 < codeLength && j + 1 < codeLength && code[i][j + 1] && code[i + 1][j + 1] && code[i + 1][j]) {
                        //随机取图片，画100*100的图
                        int s4 = random.nextInt(max);
                        gs.drawImage(img4[s4], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 2 * pix, 2 * pix, null);
                        code[i][j + 1] = code[i + 1][j] = code[i + 1][j + 1] = false;
                        String code1 = "" + (i + 1) + ":" + j;
                        String code2 = "" + (i) + ":" + (j + 1);
                        String code3 = "" + (i + 1) + ":" + (j + 1);
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
                        String code1 = "" + (i) + ":" + (j + 1);
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                    } else if (img2[0] != null && i + 1 < codeLength && code[i + 1][j]) {
                        //随机取图片，画100*50的图
                        int s2 = random.nextInt(max);
                        gs.drawImage(img2[s2], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 2 * pix, pix, null);
                        code[i + 1][j] = false;
                        String code1 = "" + (i + 1) + ":" + j;
                        if (!set.contains(code1)) {
                            set.add(code1);
                        }
                    } else {
                        //随机取图片，画50*50的图
                        int s1 = random.nextInt(max);
                        gs.drawImage(img1[s1], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
                } else {
                    String code1 = "" + (i) + ":" + j;
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
    private void drawQrcodeOrdi(Graphics2D gs, boolean[][] code, QRCodeVo qrCodeVo) {
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
                BufferedImage image0 = lshImageUtil.getImage(file0);
                img0[i - 1] = image0;
            }
            File file1 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\1" + i + ".png");
            if (file1.exists()) {
                BufferedImage image1 = lshImageUtil.getImage(file1);
                img1[i - 1] = image1;
            }
            File file2 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\2" + i + ".png");
            if (file2.exists()) {
                BufferedImage image2 = lshImageUtil.getImage(file2);
                img2[i - 1] = image2;
            }
            File file5 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\5" + i + ".png");
            if (file5.exists()) {
                BufferedImage image5 = lshImageUtil.getImage(file5);
                img5[i - 1] = image5;
            }
            File file6 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\6" + i + ".png");
            if (file6.exists()) {
                BufferedImage image6 = lshImageUtil.getImage(file6);
                img6[i - 1] = image6;
            }
            File file7 = new File(projectBasicInfo.getTempleUrl() + "\\" + typeCode + "\\7" + i + ".png");
            if (file7.exists()) {
                BufferedImage image7 = lshImageUtil.getImage(file7);
                img7[i - 1] = image7;
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
                        gs.drawImage(img0[iconIndex], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, pix, pix, null);
                    }
                    continue;
                }
                if (code[i][j]) {
                    if (img7[0] != null && i + 4 < codeLength && code[i + 1][j] && code[i + 2][j] && code[i + 3][j] && code[i + 4][j]) {
                        //随机取图片，画200*50的图
                        gs.drawImage(img7[iconIndex], (nowWidth - width) / 2 + i * pix + pixoff, (nowHeight - height) / 2 + j * pix + pixoff, 5 * pix, pix, null);
                        code[i + 1][j] = code[i + 2][j] = code[i + 3][j] = code[i + 4][j] = false;
                        String code1 = "" + (i + 1) + ":" + j;
                        String code2 = "" + (i + 2) + ":" + j;
                        String code3 = "" + (i + 3) + ":" + j;
                        String code4 = "" + (i + 4) + ":" + j;
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
                        String code1 = "" + (i + 1) + ":" + j;
                        String code2 = "" + (i + 2) + ":" + j;
                        String code3 = "" + (i + 3) + ":" + j;
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
                        String code1 = "" + (i + 1) + ":" + j;
                        String code2 = "" + (i + 2) + ":" + j;
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
                        String code1 = "" + (i + 1) + ":" + j;
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
    private String outPutImage(Map<Integer, BufferedImage> images, QRCodeVo qrCodeVo, boolean ifTest, boolean ifModel) {
        String filePath;
        //将文件输出
        if (!ifModel) {//商家创建
            if (ifTest) {
                filePath = projectBasicInfo.getQrcodeUrl() + "\\test\\" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_").format(new Date()) + qrCodeVo.getFileName() + "_test.jpg";
            } else {
                filePath = lshImageUtil.createPath(projectBasicInfo.getQrcodeUrl() + "\\" + qrCodeVo.getBusinessCode() + "\\" + new SimpleDateFormat("yyyy_MM_dd").format(new Date())) + "\\" + new SimpleDateFormat("HH_mm_ss_").format(new Date()) + qrCodeVo.getFileName() + ".jpg";
            }
        } else {//操作员创建
            filePath = projectBasicInfo.getModelUrl() + "\\" + qrCodeVo.getFileName() + ".jpg";
        }
        if (isMp4) {
            filePath = filePath.substring(0, filePath.lastIndexOf(".")) + ".gif";
            new LSHGifUtil().jpgToGif(images, filePath, qrCodeVo.getQrCodeTemple().getFrame());
        } else {
            lshImageUtil.sendImage(filePath, images.get(0));
        }
        return filePath;
    }

}