package com.lushihao.qrcode.util;

import org.springframework.stereotype.Component;

@Component
public class LSHCharUtil {

    /**
     * 汉字长度
     *
     * @param str
     * @return
     */
    public int charLength(String str) {
        int num = 0;
        for (char c : str.toCharArray()) {
            if (isChineseChar(c)) {
                num += 3;
            } else {
                num += 1;
            }
        }
        return num;
    }

    /**
     * 是否是汉字
     *
     * @param c
     * @return
     */
    public boolean isChineseChar(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }

}
