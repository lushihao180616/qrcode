package com.lushihao.qrcode.service.business;

import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;

import java.util.Map;

public interface BusinessService {

    Result create(Business business, String logoSrc);

    Result update(Business business, String logoSrc);

    Result delete(String code);

    Map<String, Object> filter(Business business);

}
