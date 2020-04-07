package com.lushihao.qrcode.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class LSHImageUtil {

    private static final String DEFAULTTYPE = "jpg";

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
        return sendImage(new File(path), bufferedImage, DEFAULTTYPE);
    }

    /**
     * 输出图片
     *
     * @param path
     * @return
     */
    public boolean sendImage(String path, BufferedImage bufferedImage, String type) {
        return sendImage(new File(path), bufferedImage, type);
    }

    /**
     * 输出图片
     *
     * @param file
     * @return
     */
    public boolean sendImage(File file, BufferedImage bufferedImage, String type) {
        boolean back = false;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            back = ImageIO.write(bufferedImage, type, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        } else {
            File[] fs = file.listFiles();
            for (File f : fs) {
                f.delete();
            }
            file.delete();
        }
    }

    /**
     * 删除文件夹下的文件
     *
     * @param dirPath
     */
    public void delDirFile(String dirPath) {
        File file = new File(dirPath);
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            for (File f : fs) {
                f.delete();
            }
        }
    }

    /**
     * 拷贝文件夹
     *
     * @param srcPath
     * @param destPath
     */
    public void copyDirectory(String srcPath, String destPath) {
        //初始化文件复制
        File file1 = new File(srcPath);
        //把文件里面内容放进数组
        File[] fs = file1.listFiles();
        //遍历文件及文件夹
        for (File f : fs) {
            if (f.isFile()) {
                //文件
                copyFile(f.getPath(), destPath + "\\" + f.getName()); //调用文件拷贝的方法
            }
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
        copy(srcPath, destPath);
    }

    public static void copy(String fileName1, String fileName2) {
        File file1 = new File(fileName1);
        File file2 = new File(fileName2);
        if (file1 == null || !file1.exists()) {
            return;
        }
        if (file2 == null) {
            return;
        } else {
            file2.getParentFile().mkdirs();
        }
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file1);
            fileOutputStream = new FileOutputStream(file2);
            byte[] b = new byte[1024 * 1024];
            int length = fileInputStream.read(b);
            while (length != -1) {
                fileOutputStream.write(b, 0, length);
                length = fileInputStream.read(b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件夹
     *
     * @param qrcodePath
     * @return
     */
    public String createPath(String qrcodePath) {
        File qrcodeDirectory = new File(qrcodePath);
        if (!qrcodeDirectory.exists()) {//如果文件夹不存在
            qrcodeDirectory.mkdir();//创建文件夹
        }
        return qrcodePath;
    }

}
