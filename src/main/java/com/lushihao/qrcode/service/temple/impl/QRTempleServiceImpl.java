package com.lushihao.qrcode.service.temple.impl;

import com.alibaba.druid.util.StringUtils;
import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.dao.QRTempleMapper;
import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.qrcode.QRCode;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.entity.yml.UserBasicInfo;
import com.lushihao.qrcode.init.InitProject;
import com.lushihao.qrcode.service.temple.QRTempleService;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import com.lushihao.qrcode.util.LSHFtpUtil;
import com.lushihao.qrcode.util.LSHImageUtil;
import com.lushihao.qrcode.util.LSHQRCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Resource
    private LSHFtpUtil lshFtpUtil;
    @Resource
    private InitProject initProject;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserBasicInfo userBasicInfo;

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
            QRCode qrCode = new QRCode("超级码丽", "text", qrTempleMapper.filter(qrCodeTemple.getCode()).get(0), "00000000", "00000000", qrCodeTemple.getCode(), null, 1950, 50, 50, 0, qrCodeTemple.getAngle(), 0);
            if (qrCode.getQrCodeTemple().isIfSelfBg()) {
                if (!qrCode.getQrCodeTemple().isIfGif()) {
                    qrCode.setBackGround(projectBasicInfo.getTempleUrl() + "\\" + qrCode.getQrCodeTemple().getCode() + "\\bg.jpg");
                } else {
                    qrCode.setBackGround(projectBasicInfo.getTempleUrl() + "\\" + qrCode.getQrCodeTemple().getCode() + "\\bg.gif");
                }
            }
            lshqrCodeUtil.qrcode(qrCode, false, true);
            initProject.getAllTemple();
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
            QRCode qrCode = new QRCode("超级码丽", "text", qrTempleMapper.filter(qrCodeTemple.getCode()).get(0), "00000000", "00000000", qrCodeTemple.getCode(), null, 1950, 50, 50, 0, qrCodeTemple.getAngle(), 0);
            if (qrCode.getQrCodeTemple().isIfSelfBg()) {
                if (!qrCode.getQrCodeTemple().isIfGif()) {
                    qrCode.setBackGround(projectBasicInfo.getTempleUrl() + "\\" + qrCode.getQrCodeTemple().getCode() + "\\bg.jpg");
                } else {
                    qrCode.setBackGround(projectBasicInfo.getTempleUrl() + "\\" + qrCode.getQrCodeTemple().getCode() + "\\bg.gif");
                }
            }
            lshqrCodeUtil.qrcode(qrCode, false, true);
            initProject.getAllTemple();
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
            initProject.getAllTemple();
            return new Result(true, filter(null), "删除成功", null);
        }
    }

    @Override
    @Transactional
    public List<Map<String, Object>> filter(String code) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<QRCodeTemple> templeList = new ArrayList<>();
        for (File file : new File(projectBasicInfo.getTempleUrl()).listFiles()) {
            if (file.isDirectory()) {
                templeList.addAll(initProject.qrCodeTempleList.stream().filter(s -> StringUtils.equals(s.getCode(), file.getName())).collect(Collectors.toList()));
            }
        }
        if (code != null && !"".equals(code)) {
            templeList = templeList.stream().filter(s -> s.getCode().startsWith(code)).collect(Collectors.toList());
        }
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
    public Result downLoad(String downLoadTempleCode, boolean flag) {
        boolean subBean = false;
        List<QRCodeTemple> nowTemple = initProject.qrCodeTempleList.stream().filter(s -> StringUtils.equals(s.getCode(), downLoadTempleCode)).collect(Collectors.toList());
        if (nowTemple == null || nowTemple.size() == 0) {
            return new Result(false, null, null, "下载的模板不存在，请检查模板号是否正确");
        } else {
            if (nowTemple.get(0).getMoney() > 0) {
                if (!flag) {
                    return new Result(true, "下载此模板需要花费" + nowTemple.get(0).getMoney() + "金豆", null, null);
                } else {
                    if (!userInfoService.countSub((int) nowTemple.get(0).getMoney(), userBasicInfo.getCode())) {
                        return new Result(false, null, null, "金豆不够用了");
                    } else {
                        subBean = true;
                    }
                }
            }
        }
        if (initProject.bucketTemple == null) {
            if (subBean) {
                userInfoService.countAdd((int) nowTemple.get(0).getMoney(), userBasicInfo.getCode());
            }
            return new Result(false, null, null, "网络连接失败");
        }
        if (lshFtpUtil.connectServer(initProject.bucketTemple.getIp(), Integer.valueOf(initProject.bucketTemple.getPort()), initProject.bucketTemple.getUserName(), initProject.bucketTemple.getPwd())) {
            File dir = new File(projectBasicInfo.getTempleUrl() + "\\" + downLoadTempleCode);
            dir.mkdir();
            List<String> notCopyFileNames = new ArrayList<>();
            String notCopyFileName;
            if (downLoadTempleCode.startsWith("D")) {
                notCopyFileName = downLoadTempleCode + ".gif";
            } else {
                notCopyFileName = downLoadTempleCode + ".jpg";
            }
            notCopyFileNames.add(notCopyFileName);
            if (lshFtpUtil.downloadDir(initProject.bucketTemple.getName() + "/" + downLoadTempleCode, projectBasicInfo.getTempleUrl() + "\\" + downLoadTempleCode, notCopyFileNames)) {
                lshFtpUtil.download(notCopyFileName, projectBasicInfo.getModelUrl() + "\\" + notCopyFileName);
                lshFtpUtil.closeServer();
                return new Result(true, null, "下载成功", null);
            } else {
                dir.delete();
                if (subBean) {
                    userInfoService.countAdd((int) nowTemple.get(0).getMoney(), userBasicInfo.getCode());
                }
                return new Result(false, null, null, "下载的模板不存在，请检查模板号是否正确");
            }
        } else {
            if (subBean) {
                userInfoService.countAdd((int) nowTemple.get(0).getMoney(), userBasicInfo.getCode());
            }
            return new Result(false, null, null, "网络连接失败");
        }
    }

}
