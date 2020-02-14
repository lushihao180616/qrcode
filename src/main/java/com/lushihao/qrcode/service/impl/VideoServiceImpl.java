package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

@Service
public class VideoServiceImpl implements VideoService {

    @Override
    @Transactional
    public String create() {
        return null;
    }

}
