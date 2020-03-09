package com.lushihao.qrcode.util;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Component
public class LSHGifUtil {

    /**
     * jpg转成gif
     *
     * @param jpgDirectoryPath
     * @param gifFilePath
     */
    public void jpgToGif(String jpgDirectoryPath, String gifFilePath) throws IOException {
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
                return Integer.valueOf(o1.getName().lastIndexOf(".")).compareTo(Integer.valueOf(o2.getName().lastIndexOf(".")));
            }
        });
        for (int i = 0; i < list.size(); i++) {
            map.put(i, ImageIO.read(list.get(i)));
        }
        jpgToGif(map, gifFilePath, 10);
    }

    /**
     * jpg转成gif
     *
     * @param map
     * @param gifFilePath
     */
    public boolean jpgToGif(Map<Integer, BufferedImage> map, String gifFilePath, int frame) {
        AnimatedGifEncoder e = new AnimatedGifEncoder();
        e.setRepeat(1);
        e.start(gifFilePath);//生成gif图片位置名称
        //以文件名排序
        for (int i = 0; i < map.size(); i++) {
            BufferedImage eachFrame = map.get(i);
            e.setDelay(1000 / frame);//延时
            e.addFrame(eachFrame);
        }
        e.finish();
        return true;
    }

    /**
     * gif转jpg
     *
     * @throws IOException
     */
    public synchronized Map<Integer, BufferedImage> gifToJpg(String gifFilePath) {
        Map<Integer, BufferedImage> backMap = new HashMap<>();
        GifDecoder decoder = new GifDecoder();
        InputStream is = null;
        try {
            is = new FileInputStream(gifFilePath);
            if (decoder.read(is) != 0) {
                return null;
            }
            for (int i = 0; i < decoder.getFrameCount(); i++) {
                BufferedImage frame = decoder.getFrame(i);
                backMap.put(i, frame);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return backMap;
    }

    /**
     * gif转jpg
     *
     * @throws IOException
     */
    public static synchronized void gifToJpg(String jpgDirectoryPath, String gifFilePath) throws IOException {
        Map<Integer, BufferedImage> backMap = new HashMap<>();
        GifDecoder decoder = new GifDecoder();
        InputStream is = new FileInputStream(gifFilePath);
        if (decoder.read(is) != 0) {
            return;
        }
        is.close();
        for (int i = 0; i < decoder.getFrameCount(); i++) {
            BufferedImage frame = decoder.getFrame(i);
            ImageIO.write(frame, "jpg", new FileOutputStream(jpgDirectoryPath + "\\" + (i + 1) + ".jpg"));
        }
    }
}
