package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.BusinessService;
import com.lushihao.qrcode.util.LSHImageUtil;
import com.lushihao.qrcode.util.LSHVideoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;
    @Resource
    private LSHImageUtil lshImageUtil;

    @Override
    @Transactional
    public String create(Business business, String logoSrc) {
        business.setCode(ifExitCode());
        int back = businessMapper.create(business);
        if (back > 0) {
            //商标地址
            String businessPath = projectBasicInfo.getBusinessUrl() + "\\" + business.getCode();
            File logoDirectory = new File(businessPath);
            if (!logoDirectory.exists()) {//如果文件夹不存在
                logoDirectory.mkdir();//创建文件夹
            }

            //二维码地址
            String qrcodePath = projectBasicInfo.getQrcodeUrl() + "\\" + business.getCode();
            File qrcodeDirectory = new File(qrcodePath);
            if (!qrcodeDirectory.exists()) {//如果文件夹不存在
                qrcodeDirectory.mkdir();//创建文件夹
            }
            lshImageUtil.copyFile(logoSrc, businessPath + "\\logo.png");

            return "创建成功，商家编号为" + business.getCode();
        }
        return "创建失败";
    }

    /**
     * 是否存在商家标识
     *
     * @return
     */
    private String ifExitCode() {
        String code = UUID.randomUUID().toString().substring(0, 8);
        Business business = new Business();
        business.setCode(code);
        if (businessMapper.filter(business).size() > 0) {
            return ifExitCode();
        }
        return code;
    }

    @Override
    @Transactional
    public String update(Business business, String logoSrc) {
        int back = businessMapper.update(business);
        if (back > 0) {
            if (logoSrc != null && !"".equals(logoSrc)) {
                //商标地址
                String businessPath = projectBasicInfo.getBusinessUrl() + "\\" + business.getCode();
                lshImageUtil.copyFile(logoSrc, businessPath + "\\logo.png");
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
                lshImageUtil.delFileOrDir(logoPath);
                //二维码地址
                String qrcodePath = projectBasicInfo.getQrcodeUrl() + "\\" + code;
                lshImageUtil.delFileOrDir(qrcodePath);
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

}
