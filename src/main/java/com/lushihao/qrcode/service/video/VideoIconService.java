package com.lushihao.qrcode.service.video;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.video.VideoIcon;

public interface VideoIconService {

    Result addIcon(VideoIcon videoIcon);

    Result testIcon(VideoIcon videoIcon);

}
