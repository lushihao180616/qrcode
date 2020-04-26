package com.lushihao.qrcode.dao;

import com.lushihao.qrcode.entity.bucket.Bucket;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BucketMapper {

    List<Bucket> filter();

}
