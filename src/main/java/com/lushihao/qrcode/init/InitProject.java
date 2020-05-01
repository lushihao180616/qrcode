package com.lushihao.qrcode.init;

import com.lushihao.myutils.collection.LSHMapUtils;
import com.lushihao.qrcode.dao.*;
import com.lushihao.qrcode.entity.bean.BeanCost;
import com.lushihao.qrcode.entity.bucket.Bucket;
import com.lushihao.qrcode.entity.business.Business;
import com.lushihao.qrcode.entity.manager.Manager;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;
import com.lushihao.qrcode.entity.user.UserInfo;
import com.lushihao.qrcode.config.yml.ProjectBasicInfo;
import com.lushihao.qrcode.config.yml.UserBasicInfo;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 初始化项目
 */
@Component
public class InitProject implements ApplicationRunner {

    /**
     * 当前用户信息
     */
    public static UserInfo userInfo;
    /**
     * 各种服务花费金豆
     */
    public static List<BeanCost> beanCosts;
    /**
     * 当前实用的云存储
     */
    public static Bucket bucket;
    /**
     * 服务器模板位置
     */
    public static Bucket bucketFiles;
    /**
     * 数据库全部模板
     */
    public static List<QRCodeTemple> qrCodeTempleList;
    @Resource
    private ProjectBasicInfo projectBasicInfo;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserBasicInfo userBasicInfo;
    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private ManagerMapper managerMapper;
    @Resource
    private QRTempleMapper qrTempleMapper;
    @Resource
    private BeanCostMapper beanCostMapper;
    @Resource
    private BucketMapper bucketMapper;

    /**
     * 根据配置文件获取需要执行的任务，并通过任务调度器执行
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) {
        createDirectory(projectBasicInfo.getTempleUrl());
        createDirectory(projectBasicInfo.getQrcodeUrl());
        createDirectory(projectBasicInfo.getModelUrl());
        createDirectory(projectBasicInfo.getTempJpgUrl());

        getUserInfo();
        getBeanCost();
        getBucket();
        getAllTemple();
    }

    /**
     * 创建文件夹
     * @param directory
     */
    private void createDirectory(String directory) {
        File modelDirectory = new File(directory);
        if (!modelDirectory.exists()) {//如果文件夹不存在
            modelDirectory.mkdir();//创建文件夹
        }
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        List<Map<String, Object>> userInfoList = userInfoMapper.filter(userBasicInfo.getCode());

        if (userInfoList.size() > 0) {
            Map<String, Object> map = userInfoList.get(0);
            userInfo = LSHMapUtils.mapToEntity(map, UserInfo.class);
            userInfo.setCount((Integer) map.get("count"));
            userInfo.setUserType(userInfoMapper.filterType((String) map.get("typecode"), -1).get(0));
            if (userInfo.getUserType().getType().equals("1")) {//商家信息
                Business business = new Business();
                business.setCode(userBasicInfo.getCode());
                List<Business> businessList = businessMapper.filter(business);
                if (businessList.size() > 0) {
                    business = businessList.get(0);
                }
                userInfo.setBusiness(business);
            } else {//管理员信息
                Manager manager = new Manager();
                manager.setCode(userBasicInfo.getCode());
                List<Manager> managerList = managerMapper.filter(manager);
                if (managerList.size() > 0) {
                    manager = managerList.get(0);
                }
                userInfo.setManager(manager);
            }
        }
    }

    /**
     * 各种服务花费的金豆
     */
    public void getBeanCost() {
        beanCosts = beanCostMapper.filter();
    }

    /**
     * 云存储相关
     */
    public void getBucket() {
        List<Bucket> bucketList = bucketMapper.filter().stream().filter(s -> s.isIfUse() && s.getName().startsWith("cjml-qrcode")).collect(Collectors.toList());
        int randomNum = new Random().nextInt(bucketList.size());
        bucket = bucketList.get(randomNum);
        List<Bucket> bucketTempleList = bucketMapper.filter().stream().filter(s -> s.isIfUse() && s.getName().startsWith("qrcode-files")).collect(Collectors.toList());
        int randomTempleNum = new Random().nextInt(bucketTempleList.size());
        bucketFiles = bucketTempleList.get(randomTempleNum);
    }

    /**
     * 数据库全部模板
     */
    public void getAllTemple() {
        qrCodeTempleList = qrTempleMapper.filter(null);
    }

}
