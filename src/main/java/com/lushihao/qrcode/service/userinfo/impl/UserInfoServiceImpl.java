package com.lushihao.qrcode.service.userinfo.impl;

import com.lushihao.qrcode.dao.UserInfoMapper;
import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

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
        return InitProject.userInfo;
    }

}

