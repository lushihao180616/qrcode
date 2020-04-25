package com.lushihao.qrcode.dao;

import com.lushihao.qrcode.entity.bean.BeanCost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BeanCostMapper {

    List<BeanCost> filter();

}
