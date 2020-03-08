package com.lushihao.qrcode.util;

import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.SocketException;

@Component
public class LSHFtpUtil {

    @Resource
    private ProjectBasicInfo projectBasicInfo;
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

    private void setInfo(){
        this.ftpIP = projectBasicInfo.getFtpIp();
        this.ftpPort = projectBasicInfo.getFtpPort();
        this.ftpUserName = projectBasicInfo.getFtpUserName();
        this.ftpPassword = projectBasicInfo.getFtpPassword();
    }

    /**
     * 连接服务器
     *
     * @return
     */
    public synchronized boolean connectServer() {
        setInfo();
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
        setInfo();
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
     * @param sourcePath
     * @param fileName
     * @param targetPath
     * @return
     */
    public synchronized boolean download(String sourcePath, String fileName, String targetPath) {
        setInfo();
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetPath);
            //切换工作路径，设置上传的路径
            ftpClient.changeWorkingDirectory(sourcePath);
            //设置1M缓冲
            ftpClient.setBufferSize(1024);
            //设置被动模式
            ftpClient.enterLocalPassiveMode();
            //开始下载文件
            ftpClient.retrieveFile(fileName, outputStream);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        setInfo();
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

}