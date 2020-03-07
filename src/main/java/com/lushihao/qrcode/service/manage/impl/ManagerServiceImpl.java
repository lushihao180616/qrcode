package com.lushihao.qrcode.service.manage.impl;

import com.lushihao.qrcode.dao.ManagerMapper;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.service.manage.ManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerMapper managerMapper;

    @Override
    @Transactional
    public Result create(Manager manager) {
        int back = managerMapper.create(manager);
        if (back == 0) {
            return new Result(false, null, null, "创建失败");
        } else {
            return new Result(true, filter(new Manager()), "创建成功", null);
        }
    }

    @Override
    @Transactional
    public Result update(Manager manager) {
        int back = managerMapper.update(manager);
        if (back == 0) {
            return new Result(false, null, null, "更新失败");
        } else {
            return new Result(true, filter(new Manager()), "更新成功", null);
        }
    }

    @Override
    @Transactional
    public Result delete(String code) {
        int back = managerMapper.delete(code);
        if (back == 0) {
            return new Result(false, null, null, "删除失败");
        } else {
            return new Result(true, filter(new Manager()), "删除成功", null);
        }
    }

    @Override
    @Transactional
    public List<Manager> filter(Manager manager) {
        return managerMapper.filter(manager);
    }

}
