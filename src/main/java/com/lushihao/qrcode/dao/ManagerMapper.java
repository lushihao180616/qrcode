package com.lushihao.qrcode.dao;

import com.lushihao.qrcode.entity.manager.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ManagerMapper {

    int create(Manager manager);

    int update(Manager manager);

    int updateSelf(Manager manager);

    int delete(@Param("code") String code);

    List<Manager> filter(Manager manager);

}
