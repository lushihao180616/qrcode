package com.lushihao.qrcode.service.business;

import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.common.Result;

import java.util.List;

public interface BusinessService {

    Result create(Business business, String logoSrc);

    Result update(Business business, String logoSrc);

    Result delete(String code);

    List<Business> filter(Business business);
}
