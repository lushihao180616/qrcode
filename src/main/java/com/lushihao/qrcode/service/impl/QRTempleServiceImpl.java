package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.dao.QRTempleMapper;
import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import com.lushihao.qrcode.entity.qrcode.QRCodeTemple;
import com.lushihao.qrcode.service.QRTempleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class QRTempleServiceImpl implements QRTempleService {

    @Resource
    private QRTempleMapper qrTempleMapper;
    @Resource
    private ProjectBasicInfo projectBasicInfo;

    @Override
    public String create(QRCodeTemple qrCodeTemple, String templeItemsPath) {
        int back = qrTempleMapper.create(qrCodeTemple);
        if (back > 0) {
            //商标地址
            String templePath = projectBasicInfo.getTempleUrl() + "\\" + qrCodeTemple.getCode();
            File templeDirectory = new File(templePath);
            if (!templeDirectory.exists()) {//如果文件夹不存在
                templeDirectory.mkdir();//创建文件夹
            }
            //下面需要拷贝文件夹中所有文件
            copyDirectory(templeItemsPath.substring(0, templeItemsPath.lastIndexOf("\\")), templePath);
            return "创建成功";
        }
        return "创建失败";
    }

    @Override
    public String update(QRCodeTemple qrCodeTemple, String templeItemsPath) {
        int back = qrTempleMapper.update(qrCodeTemple);
        if (back > 0) {
            if (templeItemsPath != null && !"".equals(templeItemsPath)) {
                //商标地址
                String templePath = projectBasicInfo.getTempleUrl() + "\\" + qrCodeTemple.getCode();
                //下面需要拷贝文件夹中所有文件
                copyDirectory(templeItemsPath.substring(0, templeItemsPath.lastIndexOf("\\")), templePath);
            }
            return "更新成功";
        }
        return "更新失败";
    }

    @Override
    public String delete(String code) {
        int back = qrTempleMapper.delete(code);
        if (back > 0) {
//            //模板地址
//            String logoPath = projectBasicInfo.getTempleUrl() + "\\" + code;
//            delFile(logoPath);

            return "删除成功";
        }
        return "删除失败";
    }

    @Override
    public List<QRCodeTemple> filter(String code) {
        return qrTempleMapper.filter(code);
    }

    /**
     * 拷贝文件夹
     *
     * @param from
     * @param to
     */
    private void copyDirectory(String from, String to) {
        //初始化文件复制
        File file1 = new File(from);
        //把文件里面内容放进数组
        File[] fs = file1.listFiles();
        //遍历文件及文件夹
        for (File f : fs) {
            if (f.isFile()) {
                //文件
                copyFile(f.getPath(), to + "\\" + f.getName()); //调用文件拷贝的方法
            }
        }
    }

    /**
     * 拷贝文件
     *
     * @param srcPath
     * @param destPath
     * @throws IOException
     */
    private void copyFile(String srcPath, String destPath) {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            // 打开输入流
            fis = new FileInputStream(srcPath);
            // 打开输出流
            fos = new FileOutputStream(destPath);

            // 读取和写入信息
            int len = 0;
            while ((len = fis.read()) != -1) {
                fos.write(len);
            }
        } catch (Exception e) {
        } finally {
            // 关闭流  先开后关  后开先关
            try {
                fos.close(); // 后开先关
                fis.close(); // 先开后关
            } catch (IOException e) {
            }
        }
    }

    /**
     * 删除文件或文件夹下所有内容
     * @param filename
     */
    private void delFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        } else {
            File[] fs = file.listFiles();
            for (File f : fs) {
                f.delete();
            }
            file.delete();
            return;
        }
    }

}
