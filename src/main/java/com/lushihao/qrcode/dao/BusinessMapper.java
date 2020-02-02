package com.lushihao.qrcode.dao;

import com.lushihao.qrcode.entity.business.Business;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusinessMapper {

    int create(Business business);

    int update(Business business);

    int delete(@Param("code") String code);

    List<Business> filter(Business business);
}
