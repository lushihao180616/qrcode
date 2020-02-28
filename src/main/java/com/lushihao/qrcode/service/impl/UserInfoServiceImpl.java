package com.lushihao.qrcode.service.impl;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.dao.UserInfoMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.entity.yml.UserBasicInfo;
import com.lushihao.qrcode.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserBasicInfo userBasicInfo;
    @Resource
    private BusinessMapper businessMapper;

    @Override
    public String create(UserInfo userInfo) {
        userInfo.setUserType(userInfoMapper.filterType("06").get(0));
        int back = userInfoMapper.create(userInfo);
        if (back > 0) {
            return "创建成功";
        }
        return "创建失败";
    }

    @Override
    public UserInfo filter() {
        UserInfo userInfo = null;
        List<Map<String, Object>> userInfoList = userInfoMapper.filter(userBasicInfo.getCode());

        if (userInfoList.size() > 0) {
            Map<String, Object> map = userInfoList.get(0);
            userInfo = LSHMapUtils.mapToEntity(map, UserInfo.class);
            userInfo.setCount((Integer) map.get("count"));
            userInfo.setUserType(userInfoMapper.filterType((String) map.get("typecode")).get(0));
        }

        Business business = new Business();
        business.setCode(userBasicInfo.getCode());
        business = businessMapper.filter(business).get(0);
        userInfo.setBusiness(business);
        return userInfo;
    }

}

