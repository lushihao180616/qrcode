package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.service.BusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
    @Transactional
    public String update(Business business, String logoSrc) {
        int back = businessMapper.update(business);
        if (back > 0) {
            if (logoSrc != null && !"".equals(logoSrc)) {
                //商标地址
                String logoPath = projectBasicInfo.getBusinessUrl() + "\\" + business.getCode();
                copyFile(logoSrc, logoPath + "\\logo.jpg");
            }

            return "更新成功";
        }
        return "更新失败";
    }

    @Override
    @Transactional
    public String delete(String code) {
        int back = businessMapper.delete(code);
        if (back > 0) {
            if (projectBasicInfo.isDeleteAllBusinessFiles()) {
                //商标地址
                String logoPath = projectBasicInfo.getBusinessUrl() + "\\" + code;
                delFile(logoPath);
                //二维码地址
                String qrcodePath = projectBasicInfo.getQrcodeUrl() + "\\" + code;
                delFile(qrcodePath);
            }

            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    @Transactional
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
                if(fos != null){
                    fos.close(); // 后开先关
                }
                if(fis != null){
                    fis.close(); // 先开后关
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 删除文件或文件夹下所有内容
     *
     * @param filename
     */
    private void delFile(String filename) {
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

}
