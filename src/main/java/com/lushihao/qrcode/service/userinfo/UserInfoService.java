package com.lushihao.qrcode.service.userinfo;

import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.entity.user.UserInfo;

public interface UserInfoService {

    UserInfo filter();

    Result modify(Business business, String logoPath);

    Result modify(Manager manager, String logoPath);

    boolean countSub(int subCount, String code);

    boolean countAdd(int addCount, String code);

}
