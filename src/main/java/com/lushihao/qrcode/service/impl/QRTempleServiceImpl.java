package com.lushihao.qrcode.service.impl;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.dao.QRTempleMapper;
import com.lushihao.qrcode.entity.qrcode.QRCodeRequest;
import com.lushihao.qrcode.entity.qrcode.QRCodeVo;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.service.QRTempleService;
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
    public String create(QRCodeTemple qrCodeTemple, String templeItemsPath) {
        int back = qrTempleMapper.create(qrCodeTemple);
        if (back > 0) {
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
            QRCodeRequest qrCodeRequest = new QRCodeRequest("超级码丽", qrCodeTemple.getCode(), "00000000", qrCodeTemple.getCode(), null, 1950, 0, 0, 0, 0);
            QRCodeVo qrCodeVo = new QRCodeVo(qrCodeRequest.getMessage(), qrTempleMapper.filter(qrCodeRequest.getTempleCode()).get(0), qrCodeRequest.getBusinessCode(), qrCodeRequest.getFileName(), qrCodeRequest.getBackGround(), qrCodeRequest.getShortLength(), qrCodeRequest.getX(), qrCodeRequest.getY(), qrCodeRequest.getAlpha(), qrCodeRequest.getAngle());
            lshqrCodeUtil.qrcode(qrCodeVo, false, true);
            return "创建成功";
        }
        return "创建失败";
    }

    @Override
    @Transactional
    public String update(QRCodeTemple qrCodeTemple, String templeItemsPath) {
        int back = qrTempleMapper.update(qrCodeTemple);
        if (back > 0) {
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
            QRCodeRequest qrCodeRequest = new QRCodeRequest("超级码丽", qrCodeTemple.getCode(), "00000000", qrCodeTemple.getCode(), null, 1950, 0, 0, 0, 0);
            QRCodeVo qrCodeVo = new QRCodeVo(qrCodeRequest.getMessage(), qrTempleMapper.filter(qrCodeRequest.getTempleCode()).get(0), qrCodeRequest.getBusinessCode(), qrCodeRequest.getFileName(), qrCodeRequest.getBackGround(), qrCodeRequest.getShortLength(), qrCodeRequest.getX(), qrCodeRequest.getY(), qrCodeRequest.getAlpha(), qrCodeRequest.getAngle());
            lshqrCodeUtil.qrcode(qrCodeVo, false, true);
            return "更新成功";
        }
        return "更新失败";
    }

    @Override
    @Transactional
    public String delete(String code) {
        int back = qrTempleMapper.delete(code);
        if (back > 0) {
            if (projectBasicInfo.isDeleteAllTempleFiles()) {
                //模板地址
                String logoPath = projectBasicInfo.getTempleUrl() + "\\" + code;
                lshImageUtil.delFileOrDir(logoPath);
                //模板地址
                String modelPath = projectBasicInfo.getModelUrl() + "\\" + code + ".jpg";
                lshImageUtil.delFileOrDir(modelPath);
            }

            return "删除成功";
        }
        return "删除失败";
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

    @Override
    @Transactional
    public String downLoad(String downLoad) {
        // 解码
        // 下载模板、生成数据库数据
        return "下载成功";
    }

}
