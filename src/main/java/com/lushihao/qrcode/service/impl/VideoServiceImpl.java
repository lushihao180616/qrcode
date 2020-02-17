package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.entity.video.Video;
import com.lushihao.qrcode.service.VideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoServiceImpl implements VideoService {

    @Override
    @Transactional
    public String create(Video video) {

        return null;
    }

}
