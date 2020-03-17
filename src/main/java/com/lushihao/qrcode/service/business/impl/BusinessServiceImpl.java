package com.lushihao.qrcode.service.business.impl;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.dao.UserInfoMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.entity.user.UserType;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.business.BusinessService;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;
    @Resource
    private LSHImageUtil lshImageUtil;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public Result create(Business business, String logoSrc, String typeCode, String macAddress) {
        business.setCode(getCode());
        int back = businessMapper.create(business);
        if (back == 0) {
            return new Result(false, null, null, "创建失败");
        } else {
            UserInfo userInfo = new UserInfo(business.getCode(), userInfoMapper.filterType(typeCode, 1).get(0), 0, macAddress, business, null);
            userInfoMapper.create(userInfo);
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
    public Result update(Business business, String logoSrc, String typeCode, String macAddress) {
        int back = businessMapper.update(business);
        if (back == 0) {
            return new Result(false, null, null, "更新失败，请稍后再试");
        } else {
            UserInfo userInfo = new UserInfo(business.getCode(), userInfoMapper.filterType(typeCode, 1).get(0), 0, macAddress, business, null);
            userInfoMapper.update(userInfo);
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
            userInfoMapper.deleteUserInfo(code);
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
    public Map<String, Object> filter(Business business) {
        List<UserType> types = userInfoMapper.filterType(null, 1);
        List<Business> businesses = businessMapper.filter(business);
        List<Map<String, Object>> businessInfoList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("types", types);
        map.put("businesses", businesses);
        for (Business businessItem : businesses) {
            Map<String, Object> userInfo = userInfoMapper.filter(businessItem.getCode()).get(0);
            String code = (String) userInfo.get("typecode");
            Map<String, Object> mapItem = LSHMapUtils.entityToMap(userInfoMapper.filterType(code, 1).get(0));
            mapItem.put("macAddress", userInfo.get("macAddress"));
            businessInfoList.add(mapItem);
        }
        map.put("type", businessInfoList);
        return map;
    }

}
