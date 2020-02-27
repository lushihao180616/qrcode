package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.user.UserInfo;

public interface UserInfoService {

    String create(UserInfo userInfo);

    UserInfo filter();

}
