package com.lushihao.qrcode.dao;

import com.lushihao.qrcode.entity.qrcode.QRCodeTemple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QRTempleMapper {

    int create(QRCodeTemple qrCodeTemple);

    List<QRCodeTemple> filter(@Param("code") String code);

}
