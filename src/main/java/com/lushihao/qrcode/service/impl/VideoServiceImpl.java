package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import com.lushihao.qrcode.entity.video.Video;
import com.lushihao.qrcode.service.VideoService;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VideoServiceImpl implements VideoService {

    @Resource
    private LSHFfmpegUtil lshFfmpegUtil;
    @Resource
    private ProjectBasicInfo projectBasicInfo;

    @Override
    public String create(Video video) {
        video.setEndPath(projectBasicInfo.getBusinessUrl() + "\\" + video.getBusinessCode() + "\\" + "endVideo.mp4");
        video.setNewVideoPath(video.getOldVideoPath().substring(0, video.getOldVideoPath().lastIndexOf(".")) + "_new.mp4");
        return lshFfmpegUtil.videoAddVideo(video.getOldVideoPath(), video.getEndPath(), video.getNewVideoPath());
    }

}
