package com.lushihao.qrcode.util;

import org.jim2mov.core.*;
import org.jim2mov.utils.MovieUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class LSHJpg2Mp4Util {

    public static void convertPicToAvi(Map<Integer, BufferedImage> map, String aviFileName, int fps, int mWidth, int mHeight) {
        // jpgs目录放置jpg图片,图片文件名为(1.jpg,2.jpg...)
        final Map<Integer, BufferedImage> nowMap = map;
        if (nowMap == null || nowMap.size() == 0) {
            return;
        }

        // 生成视频的名称
        DefaultMovieInfoProvider dmip = new DefaultMovieInfoProvider(aviFileName.substring(aviFileName.lastIndexOf("/") + 1));
        dmip.setMediaLocator("file:///" + aviFileName);
        // 设置每秒帧数
        dmip.setFPS(fps > 0 ? fps : 3); // 如果未设置，默认为3
        // 设置总帧数
        dmip.setNumberOfFrames(nowMap.size());
        // 设置视频宽和高（最好与图片宽高保持一直）
        dmip.setMWidth(mWidth > 0 ? mWidth : 1440); // 如果未设置，默认为1440
        dmip.setMHeight(mHeight > 0 ? mHeight : 860); // 如果未设置，默认为860

        try {
            new Jim2Mov(new ImageProvider() {
                public byte[] getImage(int frame) {
                    try {
                        // 设置压缩比
                        return MovieUtils.bufferedImageToJPEG(nowMap.get(frame), 1.0f);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    return null;
                }
            }, dmip, null).saveMovie(MovieInfoProvider.TYPE_AVI_MJPEG);
        } catch (MovieSaveException e) {
            System.err.println(e);
        }

    }

}
