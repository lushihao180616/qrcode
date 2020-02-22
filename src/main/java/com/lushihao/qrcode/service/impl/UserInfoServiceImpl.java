package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.UserInfoMapper;
import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public String create(UserInfo userInfo) {
        int back = userInfoMapper.create(userInfo);
        if (back > 0) {
            return "创建成功";
        }
        return "创建失败";
    }

}
