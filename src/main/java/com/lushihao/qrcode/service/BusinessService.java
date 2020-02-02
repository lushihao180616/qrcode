package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.business.Business;

import java.util.List;

public interface BusinessService {

    String create(Business business, String logoSrc);

    String update(Business business, String logoSrc);

    String delete(String code);

    List<Business> filter(Business business);
}
