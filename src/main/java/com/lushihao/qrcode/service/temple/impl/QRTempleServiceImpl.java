package com.lushihao.qrcode.service.temple.impl;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.dao.QRTempleMapper;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.qrcode.QRCode;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.temple.QRTempleService;
import com.lushihao.qrcode.util.LSHImageUtil;
import com.lushihao.qrcode.util.LSHQRCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QRTempleServiceImpl implements QRTempleService {

    @Resource
    private QRTempleMapper qrTempleMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;
    @Resource
    private LSHQRCodeUtil lshqrCodeUtil;
    @Resource
    private LSHImageUtil lshImageUtil;

    @Override
    @Transactional
    public Result create(QRCodeTemple qrCodeTemple, String templeItemsPath) {
        int back = qrTempleMapper.create(qrCodeTemple);
        if (back == 0) {
            return new Result(false, null, null, "创建失败");
        } else {
            //模板地址
            String templePath = projectBasicInfo.getTempleUrl() + "\\" + qrCodeTemple.getCode();
            File templeDirectory = new File(templePath);
            if (!templeDirectory.exists()) {//如果文件夹不存在
                templeDirectory.mkdir();//创建文件夹
            }
            //下面需要拷贝文件夹中所有文件
            lshImageUtil.copyDirectory(templeItemsPath.substring(0, templeItemsPath.lastIndexOf("\\")), templePath);

            String modelPath = projectBasicInfo.getModelUrl();
            File modelDirectory = new File(modelPath);
            if (!modelDirectory.exists()) {//如果文件夹不存在
                modelDirectory.mkdir();//创建文件夹
            }
            lshqrCodeUtil.qrcode(new QRCode("超级码丽", "text", qrTempleMapper.filter(qrCodeTemple.getCode()).get(0), "00000000", "00000000", qrCodeTemple.getCode(), null, 1950, 0, 0, 0, 0), false, true);
            return new Result(true, filter(null), "创建成功", null);
        }
    }

    @Override
    @Transactional
    public Result update(QRCodeTemple qrCodeTemple, String templeItemsPath) {
        int back = qrTempleMapper.update(qrCodeTemple);
        if (back == 0) {
            return new Result(false, null, null, "更新失败");
        } else {
            if (templeItemsPath != null && !"".equals(templeItemsPath)) {
                //商标地址
                String templePath = projectBasicInfo.getTempleUrl() + "\\" + qrCodeTemple.getCode();
                //下面需要拷贝文件夹中所有文件
                lshImageUtil.copyDirectory(templeItemsPath.substring(0, templeItemsPath.lastIndexOf("\\")), templePath);
            }

            String modelPath = projectBasicInfo.getModelUrl();
            File modelDirectory = new File(modelPath);
            if (!modelDirectory.exists()) {//如果文件夹不存在
                modelDirectory.mkdir();//创建文件夹
            }
            lshqrCodeUtil.qrcode(new QRCode("超级码丽", "text", qrTempleMapper.filter(qrCodeTemple.getCode()).get(0), "00000000", "00000000", qrCodeTemple.getCode(), null, 1950, 0, 0, 0, 0), false, true);
            return new Result(true, filter(null), "更新成功", null);
        }
    }

    @Override
    @Transactional
    public Result delete(String code) {
        int back = qrTempleMapper.delete(code);
        if (back == 0) {
            return new Result(false, null, null, "删除失败");
        } else {
            if (projectBasicInfo.isDeleteAllTempleFiles()) {
                //模板地址
                String logoPath = projectBasicInfo.getTempleUrl() + "\\" + code;
                lshImageUtil.delFileOrDir(logoPath);
                //模板地址
                String modelPath = projectBasicInfo.getModelUrl() + "\\" + code + ".jpg";
                lshImageUtil.delFileOrDir(modelPath);
            }
            return new Result(true, filter(null), "删除成功", null);
        }
    }

    @Override
    @Transactional
    public List<Map<String, Object>> filter(String code) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<QRCodeTemple> templeList = qrTempleMapper.filter(code);
        for (QRCodeTemple qrCodeTemple : templeList) {
            Map<String, Object> map = LSHMapUtils.entityToMap(qrCodeTemple);
            if (qrCodeTemple.isIfGif()) {
                map.put("path", projectBasicInfo.getModelUrl() + "\\" + qrCodeTemple.getCode() + ".gif");
            } else {
                map.put("path", projectBasicInfo.getModelUrl() + "\\" + qrCodeTemple.getCode() + ".jpg");
            }
            map.put("frame", map.get("frame") + "/" + map.get("startQRFrame") + "/" + map.get("endQRFrame"));
            list.add(map);
        }
        return list;
    }

}
