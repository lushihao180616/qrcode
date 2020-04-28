package com.lushihao.qrcode.util;

import com.lushihao.qrcode.init.InitProject;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;
import sun.net.ftp.FtpClient;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.net.URL;

@Component
public class LSHFtpUtil {

    /**
     * 连接客户端
     */
    private FTPClient ftpClient;
    /**
     * ip地址
     */
    private String ftpIP;
    /**
     * 端口
     */
    private Integer ftpPort;
    /**
     * 用户名
     */
    private String ftpUserName;
    /**
     * 密码
     */
    private String ftpPassword;
    /**
     * 编码格式
     */
    private String ftpEncode = "UTF-8";

    /**
     * 连接服务器
     *
     * @return
     */
    public synchronized boolean connectServer(String ip, int port, String userName, String pwd) {
        this.ftpIP = ip;
        this.ftpPort = port;
        this.ftpUserName = userName;
        this.ftpPassword = pwd;
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding(ftpEncode);//解决上传文件时文件名乱码
        int reply = 0;
        try {
            // 连接至服务器
            ftpClient.connect(ftpIP, ftpPort);
            // 登录服务器
            ftpClient.login(ftpUserName, ftpPassword);
            //登陆成功，返回码是230
            reply = ftpClient.getReplyCode();
            // 判断返回码是否合法
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return false;
            }
            //设置以二进制方式传输
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory("/");
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param targetName
     * @param fileName
     * @return
     */
    public synchronized boolean deleteFile(String targetName, String fileName) {
        boolean flag = false;
        try {
            //切换工作路径，设置上传的路径
            ftpClient.changeWorkingDirectory(targetName);
            //开始删除文件
            ftpClient.dele(fileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @param targetPath
     * @return
     */
    public synchronized boolean download(String fileName, String targetPath) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetPath);
            //设置1M缓冲
            ftpClient.setBufferSize(1024);
            //开始下载文件
            ftpClient.retrieveFile(fileName, outputStream);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 上传文件
     *
     * @param sourcePath
     * @param fileName
     * @param targetPath
     * @return
     */
    public synchronized boolean upload(String sourcePath, String fileName, String targetPath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourcePath);
            //切换工作路径，设置上传的路径
            ftpClient.changeWorkingDirectory(targetPath);
            //设置1M缓冲
            ftpClient.setBufferSize(1024);
            //设置被动模式
            ftpClient.enterLocalPassiveMode();
            //开始保存文件
            ftpClient.storeFile(fileName, inputStream);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载文件夹
     *
     * @param sourcePath
     * @param targetPath
     * @return
     */
    public synchronized boolean downloadDir(String sourcePath, String targetPath) {
        try {
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClient.listFiles(sourcePath);
            //切换工作路径，设置上传的路径
            ftpClient.changeWorkingDirectory(sourcePath);
            if (files.length > 0) {
                for (FTPFile file : files) {
                    download(file.getName(), targetPath + "\\" + file.getName());
                }
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上传文件夹
     *
     * @param sourcePath
     * @param targetPath
     * @return
     */
    public synchronized boolean uploadDir(String sourcePath, String targetPath) {
        File dir = new File(sourcePath);
        if (dir.isFile()) {
            return false;
        }
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                upload(sourcePath + "\\" + file.getName(), file.getName(), targetPath);
            }
        }
        return true;
    }

    public void closeServer() {
        if (ftpClient != null) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 网络图片转换为BufferedImage
     *
     * @param url
     * @return
     */
    public BufferedImage getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}