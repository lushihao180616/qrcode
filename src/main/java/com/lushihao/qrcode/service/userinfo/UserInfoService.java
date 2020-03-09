package com.lushihao.qrcode.service.userinfo;

import com.lushihao.qrcode.entity.user.UserInfo;

public interface UserInfoService {

    String create(UserInfo userInfo);

    UserInfo filter();

    boolean countSub(int subCount, String code);

    boolean countAdd(int addCount, String code);

}
