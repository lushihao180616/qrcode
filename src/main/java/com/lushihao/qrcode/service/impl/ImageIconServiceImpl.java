package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.image.ImageIcon;
import com.lushihao.qrcode.service.ImageIconService;
import com.lushihao.qrcode.util.LSHImageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ImageIconServiceImpl implements ImageIconService {

    @Resource
    private LSHImageUtil lshImageUtil;

    @Override
    public Result addIcon(ImageIcon imageIcon) {
        return null;
    }

    @Override
    public Result testIcon(ImageIcon imageIcon) {
        return null;
    }

}
