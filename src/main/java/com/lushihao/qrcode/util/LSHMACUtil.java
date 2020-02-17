package com.lushihao.qrcode.util;

import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Base64;

@Component
public class LSHMACUtil {

    @Resource
    private ProjectBasicInfo projectBasicInfo;

    public String getLocalMac() {
        StringBuffer sb = new StringBuffer("");
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
            //获取网卡，获取地址
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return sb.toString().toUpperCase();
    }

    public boolean check() {
        boolean back = false;
        try {
            byte[] bytes = Base64Utils.decode(projectBasicInfo.getMacAddress().getBytes());
            String str = new String(bytes);
            String str1 = str.substring(0, 5);
            String str2 = str.substring(5, 10);
            String str3 = str.substring(10);
            String str4 = str2 + str1 + str3;
            str4 = str4.substring(0, str4.length() - 3) + "=";
            byte[] macbytes = Base64.getDecoder().decode(str4);
            String mac = new String(macbytes);
            if (!mac.equals(getLocalMac())) {
                back = false;
            } else {
                back = true;
            }
        } catch (Exception e) {
        }
        return back;
    }

}
