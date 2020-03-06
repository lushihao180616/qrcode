package com.lushihao.qrcode.service.impl;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoCut;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.service.VideoCutService;
import com.lushihao.qrcode.util.LSHFfmpegUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VideoCutServiceImpl implements VideoCutService {

    @Resource
    private LSHFfmpegUtil lshFfmpegUtil;

    @Override
    public Result addCut(VideoCut videoCut) {
        VideoInfo videoInfo = lshFfmpegUtil.getVideoInfo(videoCut.getPath());
        if (videoInfo == null) {
            return new Result(false, null, null, "读取文件信息失败");
        }
        if (lshFfmpegUtil.checkFileType(videoCut.getPath()) != lshFfmpegUtil.VIDEO) {
            return new Result(false, null, null, "不支持此文件格式");
        }
        videoCut.setNewPath(videoCut.getPath().substring(0, videoCut.getPath().lastIndexOf(".")) + "_cut.mp4");
        if (!lshFfmpegUtil.videoCut(videoCut)) {
            return new Result(false, null, null, "添加失败，请重启软件后再试");
        } else {
            return new Result(true, videoCut.getNewPath(), "截取成功", null);
        }
    }

}
