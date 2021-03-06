package com.lushihao.qrcode.service.temple;

import com.lushihao.qrcode.entity.common.Result;
import com.lushihao.qrcode.entity.temple.QRCodeTemple;

import java.util.List;
import java.util.Map;

public interface QRTempleService {

    Result create(QRCodeTemple qrCodeTemple, String templeItemsPath);

    Result update(QRCodeTemple qrCodeTemple, String templeItemsPath);

    Result delete(String code);

    List<Map<String, Object>> filter(String code);

    Result downLoad(String downLoadTempleCode, boolean flag);

}
