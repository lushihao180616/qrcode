package com.lushihao.qrcode.dao;

import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.entity.user.UserType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoMapper {

    int create(UserInfo userInfo);

    int update(UserInfo userInfo);

    int deleteUserInfo(@Param("code") String code);

    List<Map<String, Object>> filter(@Param("code") String code);

    List<UserType> filterType(@Param("code") String code, @Param("type") int type);

    int countSub(@Param("subCount") int subCount, @Param("code") String code);

    int countAdd(@Param("addCount") int addCount, @Param("code") String code);

}
