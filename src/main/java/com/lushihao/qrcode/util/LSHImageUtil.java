package com.lushihao.qrcode.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class LSHImageUtil {

    /**
     * 获得图片
     *
     * @param path
     * @return
     */
    public BufferedImage getImage(String path) {
        return getImage(new File(path));
    }

    /**
     * 获得图片
     *
     * @param file
     * @return
     */
    public BufferedImage getImage(File file) {
        BufferedImage back = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            back = ImageIO.read(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return back;
    }

    /**
     * 输出图片
     *
     * @param path
     * @return
     */
    public boolean sendImage(String path, BufferedImage bufferedImage) {
        return sendImage(new File(path), bufferedImage);
    }

    /**
     * 输出图片
     *
     * @param file
     * @return
     */
    public boolean sendImage(File file, BufferedImage bufferedImage) {
        boolean back = false;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            back = ImageIO.write(bufferedImage, "jpg", fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return back;
    }

    /**
     * 删除文件或文件夹下所有内容
     *
     * @param filename
     */
    public void delFileOrDir(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        } else {
            File[] fs = file.listFiles();
            for (File f : fs) {
                f.delete();
            }
            file.delete();
            return;
        }
    }

    /**
     * 拷贝文件
     *
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    public void copyFile(String srcPath, String destPath) {
        BufferedImage bufferedImage = getImage(srcPath);
        sendImage(destPath, bufferedImage);
    }

}
