package com.lushihao.qrcode.service.business.impl;

import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.business.BusinessService;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
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
    public Result create(Business business, String logoSrc) {
        business.setCode(getCode());
        int back = businessMapper.create(business);
        if (back == 0) {
            return new Result(false, null, null, "创建失败");
        } else {
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

            return new Result(true, filter(new Business()), "创建成功，商家编号为" + business.getCode(), null);
        }
    }

    /**
     * 是否存在商家标识
     *
     * @return
     */
    private String getCode() {
        String code = UUID.randomUUID().toString().substring(0, 8);
        Business business = new Business();
        business.setCode(code);
        if (businessMapper.filter(business).size() > 0) {
            return getCode();
        }
        return code;
    }

    @Override
    @Transactional
    public Result update(Business business, String logoSrc) {
        int back = businessMapper.update(business);
        if (back == 0) {
            return new Result(false, null, null, "更新失败，请稍后再试");
        } else {
            if (logoSrc != null && !"".equals(logoSrc)) {
                //商标地址
                String businessPath = projectBasicInfo.getBusinessUrl() + "\\" + business.getCode();
                lshImageUtil.copyFile(logoSrc, businessPath + "\\logo.png");
            }
            return new Result(true, filter(new Business()), "更新成功", null);
        }
    }

    @Override
    @Transactional
    public Result delete(String code) {
        int back = businessMapper.delete(code);
        if (back == 0) {
            return new Result(false, null, null, "删除失败");
        } else {
            if (projectBasicInfo.isDeleteAllBusinessFiles()) {
                //商标地址
                String logoPath = projectBasicInfo.getBusinessUrl() + "\\" + code;
                lshImageUtil.delFileOrDir(logoPath);
                //二维码地址
                String qrcodePath = projectBasicInfo.getQrcodeUrl() + "\\" + code;
                lshImageUtil.delFileOrDir(qrcodePath);
            }

            return new Result(true, filter(new Business()), "删除成功", null);
        }
    }

    @Override
    @Transactional
    public List<Business> filter(Business business) {
        return businessMapper.filter(business);
    }

}
