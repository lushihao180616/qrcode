package com.lushihao.qrcode.service.video.impl;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoCut;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.entity.yml.UserBasicInfo;
import com.lushihao.qrcode.service.userinfo.UserInfoService;
import com.lushihao.qrcode.service.video.VideoCutService;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VideoCutServiceImpl implements VideoCutService {

    @Resource
    private LSHFfmpegUtil lshFfmpegUtil;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserBasicInfo userBasicInfo;

    @Override
    public Result addCut(VideoCut videoCut) {
        if (lshFfmpegUtil.checkFileType(videoCut.getPath()) != lshFfmpegUtil.VIDEO) {
            return new Result(false, null, null, "不支持此文件格式");
        }
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoCut.getPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        int subCount = 1;
        if (!userInfoService.countSub(subCount, userBasicInfo.getCode())) {
            return new Result(false, null, null, "金豆不够用了");
        }
        videoCut.setNewPath(videoCut.getPath().substring(0, videoCut.getPath().lastIndexOf(".")) + "_cut.mp4");
        if (!lshFfmpegUtil.videoCut(videoCut)) {
            userInfoService.countAdd(subCount, userBasicInfo.getCode());
            return new Result(false, null, null, "添加失败，请重启软件后再试");
        } else {
            return new Result(true, videoCut.getNewPath(), "截取成功", null);
        }
    }

}