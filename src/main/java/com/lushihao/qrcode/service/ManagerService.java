package com.lushihao.qrcode.service;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.manager.Manager;

import java.util.List;

public interface ManagerService {

    Result create(Manager manager);

    Result update(Manager manager);

    Result delete(String code);

    List<Manager> filter(Manager manager);

}
