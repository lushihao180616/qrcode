package com.lushihao.qrcode.util;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class LSHGif2JpgUtil {

    /**
     * jpg转成gif
     *
     * @param jpgDirectoryPath
     * @param gifFilePath
     */
    public static void jpgToGif(String jpgDirectoryPath, String gifFilePath) throws IOException {
        Map<Integer, BufferedImage> map = new HashMap<>();
        //初始化文件复制
        File file1 = new File(jpgDirectoryPath);
        //把文件里面内容放进数组
        File[] fs = file1.listFiles();
        //遍历文件及文件夹
        List<File> list = Arrays.asList(fs);
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return Integer.valueOf(o1.getName().indexOf(".jpg")).compareTo(Integer.valueOf(o2.getName().indexOf(".jpg")));
            }
        });
        for (int i = 0; i < list.size(); i++) {
            map.put(i, ImageIO.read(list.get(i)));
        }
        jpgToGif(map, gifFilePath);
    }

    /**
     * jpg转成gif
     *
     * @param map
     * @param gifFilePath
     */
    public static void jpgToGif(Map<Integer, BufferedImage> map, String gifFilePath) throws IOException {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.setRepeat(1);
        e.start(gifFilePath);//生成gif图片位置名称
        //以文件名排序
        for (int i = 0; i < map.size(); i++) {
            BufferedImage frame = map.get(i);
            e.setDelay(150);//延时
            e.addFrame(frame);
        }
        e.finish();
    }

    /**
     * gif转jpg
     *
     * @throws IOException
     */
    public synchronized static void gifToJpg(String gifFilePath, String jpgDirectoryPath) throws IOException {
        GifDecoder decoder = new GifDecoder();
        InputStream is = new FileInputStream(gifFilePath);
        if (decoder.read(is) != 0) {
            return;
        }
        is.close();
        for (int i = 0; i < decoder.getFrameCount(); i++) {
            BufferedImage frame = decoder.getFrame(i);
            int delay = decoder.getDelay(i);
            OutputStream out = new FileOutputStream(jpgDirectoryPath + "\\" + (i + 1) + ".jpg");
            ImageIO.write(frame, "jpg", out);// 将frame 按jpeg格式 写入out中
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            out.flush();
            out.close();
        }
    }

    /**
     * gif转jpg
     *
     * @throws IOException
     */
    public synchronized static Map<Integer, BufferedImage> gifToJpg(String gifFilePath) throws IOException {
        Map<Integer, BufferedImage> map = new HashMap<>();
        GifDecoder decoder = new GifDecoder();
        InputStream is = new FileInputStream(gifFilePath);
        if (decoder.read(is) != 0) {
            return map;
        }
        is.close();
        for (int i = 0; i < decoder.getFrameCount(); i++) {
            BufferedImage frame = decoder.getFrame(i);
            map.put(i, frame);
        }
        return map;
    }

}
