package com.lushihao.qrcode.dao;

import com.lushihao.qrcode.entity.qrcode.QRCodeRecord;
import com.lushihao.qrcode.entity.temple.TempleAnalysis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QRCodeRecordMapper {

    void create(QRCodeRecord qrCodeRecord);

    List<QRCodeRecord> select(QRCodeRecord qrCodeRecord);

    List<TempleAnalysis> selectTempleAnalysis(String date1, String date2);

}
