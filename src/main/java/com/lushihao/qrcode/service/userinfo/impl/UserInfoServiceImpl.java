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
    @Resource
    private InitProject initProject;

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
        return initProject.userInfo;
    }

    @Override
    public boolean countSub(int subCount, String code) {
        if ("0".equals(initProject.userInfo.getUserType().getType()) && initProject.userInfo.getCount() == -1) {//无限金豆
            return true;
        } else {
            if (initProject.userInfo.getCount() - subCount < 0) {
                return false;
            }
            int sqlBack = userInfoMapper.countSub(subCount, code);
            if (sqlBack == 0)
                return false;
            initProject.getUserInfo();
            return true;
        }
    }

    @Override
    public boolean countAdd(int addCount, String code) {
        if ("0".equals(initProject.userInfo.getUserType().getType()) && initProject.userInfo.getCount() == -1) {//无限金豆
            return true;
        } else {
            int sqlBack = userInfoMapper.countAdd(addCount, code);
            if (sqlBack == 0)
                return false;
            initProject.getUserInfo();
            return true;
        }
    }

}

