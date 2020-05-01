package com.lushihao.qrcode.service.userinfo.impl;

import com.lushihao.qrcode.config.yml.ProjectBasicInfo;
import com.lushihao.qrcode.dao.BusinessMapper;
import com.lushihao.qrcode.dao.ManagerMapper;
import com.lushihao.qrcode.dao.UserInfoMapper;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import com.lushihao.qrcode.util.LSHFtpUtil;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private InitProject initProject;
    @Resource
    private ManagerMapper managerMapper;
    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private LSHImageUtil lshImageUtil;
    @Resource
    private ProjectBasicInfo projectBasicInfo;
    @Resource
    private LSHFtpUtil lshFtpUtil;

    @Override
    public UserInfo filter() {
        return initProject.userInfo;
    }

    @Override
    @Transactional
    public Result modify(Business business, String logoPath) {
        try {
            boolean flag = false;
            int back = businessMapper.update(business);
            if (logoPath != null) {
                flag = true;
                lshImageUtil.copyFile(logoPath, projectBasicInfo.getLogoPath());
                if (lshFtpUtil.connectServer(initProject.bucketFiles.getIp(), Integer.valueOf(initProject.bucketFiles.getPort()), initProject.bucketFiles.getUserName(), initProject.bucketFiles.getPwd())) {
                    if (lshFtpUtil.upload(logoPath, business.getCode() + ".jpg", "/" + initProject.bucketFiles.getName() + "/logo")) {
                        lshFtpUtil.closeServer();
                    }
                }
            }
            if (back == 0) {
                return new Result(false, null, null, "修改失败");
            } else {
                if(flag){
                    return new Result(true, null, "修改成功，请重启启动服务", null);
                }
                return new Result(true, null, "修改成功", null);
            }
        } catch (Exception e) {
            return new Result(false, null, null, "修改失败");
        }
    }

    @Override
    @Transactional
    public Result modify(Manager manager, String logoPath) {
        try {
            boolean flag = false;
            int back = managerMapper.updateSelf(manager);
            if (logoPath != null) {
                flag = true;
                lshImageUtil.copyFile(logoPath, projectBasicInfo.getLogoPath());
                if (lshFtpUtil.connectServer(initProject.bucketFiles.getIp(), Integer.valueOf(initProject.bucketFiles.getPort()), initProject.bucketFiles.getUserName(), initProject.bucketFiles.getPwd())) {
                    if (lshFtpUtil.upload(logoPath, manager.getCode() + ".jpg", "/" + initProject.bucketFiles.getName() + "/logo")) {
                        lshFtpUtil.closeServer();
                    }
                }
            }
            if (back == 0) {
                if(flag){
                    return new Result(true, null, "修改成功，请重启启动服务", null);
                }
                return new Result(false, null, null, "修改失败");
            } else {
                return new Result(true, null, "修改成功", null);
            }
        } catch (Exception e) {
            return new Result(false, null, null, "修改失败");
        }
    }

    @Override
    @Transactional
    public boolean countSub(int subCount, String code) {
        if ("0".equals(initProject.userInfo.getUserType().getType()) && initProject.userInfo.getCount() == -1) //无限金豆
            return true;
        else {
            if (initProject.userInfo.getCount() - subCount < 0)
                return false;
            int sqlBack = userInfoMapper.countSub(subCount, code);
            if (sqlBack == 0)
                return false;
            initProject.getUserInfo();
            return true;
        }
    }

    @Override
    @Transactional
    public boolean countAdd(int addCount, String code) {
        if ("0".equals(initProject.userInfo.getUserType().getType()) && initProject.userInfo.getCount() == -1) //无限金豆
            return true;
        else {
            int sqlBack = userInfoMapper.countAdd(addCount, code);
            if (sqlBack == 0)
                return false;
            initProject.getUserInfo();
            return true;
        }
    }

}

