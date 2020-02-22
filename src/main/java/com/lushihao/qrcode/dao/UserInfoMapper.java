package com.lushihao.qrcode.dao;

import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.entity.user.UserType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    int create(UserInfo userInfo);

    List<UserInfo> filter(@Param("code") String code);

    List<UserType> filterType(@Param("code") String code);

}
