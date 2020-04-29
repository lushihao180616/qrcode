package com.lushihao.qrcode.service.business;

import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;

import java.util.Map;

public interface BusinessService {

    Result create(Business business, String logoSrc, String typeCode, String macAddress, String macAddress2);

    Result update(Business business, String logoSrc, String typeCode, String macAddress, String macAddress2);

    Result delete(String code);

    Map<String, Object> filter(Business business);

}
