package com.lushihao.qrcode.util;

import com.lushihao.qrcode.init.InitProject;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

@Component
public class LSHMACUtil {

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
        if (InitProject.userInfo.getMacAddress().equals(getLocalMac())) {
            return true;
        }
        return false;
    }

}
