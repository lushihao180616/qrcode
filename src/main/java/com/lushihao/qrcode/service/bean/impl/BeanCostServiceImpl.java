package com.lushihao.qrcode.service.bean.impl;

import com.lushihao.qrcode.entity.bean.BeanCost;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.bean.BeanCostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Service
public class BeanCostServiceImpl implements BeanCostService {

    @Resource
    private InitProject initProject;

    @Override
    public Result getCost(String type) {
        int count = 0;
        if (type.startsWith("qrcode")) {
            String[] strs1 = type.split(" ");
            BeanCost beanCost = initProject.beanCosts.stream().filter(s -> s.getType().equals(strs1[0]) && s.getUserType().equals(initProject.userInfo.getUserType().getCode())).collect(Collectors.toList()).get(0);
            count += beanCost.getBean();
            if (strs1.length > 1 && !"".equals(strs1[1])) {
                String[] strs2 = strs1[1].split(";");
                BeanCost beanCost2 = initProject.beanCosts.stream().filter(s -> s.getType().equals(strs2[0]) && s.getUserType().equals(initProject.userInfo.getUserType().getCode())).collect(Collectors.toList()).get(0);
                int fileSize_M = Integer.valueOf(strs2[1]);
                count += (fileSize_M + 5) / 5 * beanCost2.getBean();
            }
        } else {
            BeanCost beanCost = initProject.beanCosts.stream().filter(s -> s.getType().equals(type) && s.getUserType().equals(initProject.userInfo.getUserType().getCode())).collect(Collectors.toList()).get(0);
            count = beanCost.getBean();
        }
        return new Result(true, count, "搜索成功", null);
    }

}
