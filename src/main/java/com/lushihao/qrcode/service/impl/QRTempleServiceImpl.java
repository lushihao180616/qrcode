package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.QRTempleMapper;
import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import com.lushihao.qrcode.entity.qrcode.QRCodeTemple;
import com.lushihao.qrcode.service.QRTempleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service
public class QRTempleServiceImpl implements QRTempleService {

    @Resource
    private QRTempleMapper qrTempleMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;

    @Override
    public String create(QRCodeTemple qrCodeTemple, String templeItemsSrc) {
        int back = qrTempleMapper.create(qrCodeTemple);
        if (back > 0) {
            //商标地址
            String templePath = projectBasicInfo.getTempleUrl() + "\\" + qrCodeTemple.getCode();
            File templeDirectory = new File(templePath);
            if (!templeDirectory.exists()) {//如果文件夹不存在
                templeDirectory.mkdir();//创建文件夹
            }
            //下面需要拷贝文件夹中所有文件

            return "创建成功";
        }
        return "创建失败";
    }

    @Override
    public List<QRCodeTemple> filter(String code) {
        return qrTempleMapper.filter(code);
    }

}
