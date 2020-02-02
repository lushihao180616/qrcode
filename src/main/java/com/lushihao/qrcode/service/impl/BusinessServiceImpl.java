package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.service.BusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;

    @Override
    public String create(Business business, String logoSrc) {
        business.setCode(UUID.randomUUID().toString().substring(0, 8));

        int back = businessMapper.create(business);
        if (back > 0) {
            //商标地址
            String logoPath = projectBasicInfo.getBusinessUrl() + "\\" + business.getCode();
            File logoDirectory = new File(logoPath);
            if (!logoDirectory.exists()) {//如果文件夹不存在
                logoDirectory.mkdir();//创建文件夹
            }

            //二维码地址
            String qrcodePath = projectBasicInfo.getQrcodeUrl() + "\\" + business.getCode();
            File qrcodeDirectory = new File(qrcodePath);
            if (!qrcodeDirectory.exists()) {//如果文件夹不存在
                qrcodeDirectory.mkdir();//创建文件夹
            }

            copyFile(logoSrc, logoPath + "\\logo.jpg");

            return "创建成功，商家编号为" + business.getCode();
        }
        return "创建失败";
    }

    @Override
    public List<Business> filter(Business business) {
        return businessMapper.filter(business);
    }

    /**
     * 拷贝文件
     *
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    private void copyFile(String srcPath, String destPath) {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            // 打开输入流
            fis = new FileInputStream(srcPath);
            // 打开输出流
            fos = new FileOutputStream(destPath);

            // 读取和写入信息
            int len = 0;
            while ((len = fis.read()) != -1) {
                fos.write(len);
            }
        } catch (Exception e) {
        } finally {
            // 关闭流  先开后关  后开先关
            try {
                fos.close(); // 后开先关
                fis.close(); // 先开后关
            } catch (IOException e) {
            }
        }
    }

}
