package com.lushihao.qrcode.init;

import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

/**
 * 初始化项目
 */
@Component
public class initProject implements ApplicationRunner {

    @Resource
    private ProjectBasicInfo projectBasicInfo;

    /**
     * 根据配置文件获取需要执行的任务，并通过任务调度器执行
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) {

        createDirectory(projectBasicInfo.getTempleUrl());
        createDirectory(projectBasicInfo.getBusinessUrl());
        createDirectory(projectBasicInfo.getQrcodeUrl());
        createDirectory(projectBasicInfo.getModelUrl());

    }

    private void createDirectory(String directory) {
        File modelDirectory = new File(directory);
        if (!modelDirectory.exists()) {//如果文件夹不存在
            modelDirectory.mkdir();//创建文件夹
        }
    }

}
