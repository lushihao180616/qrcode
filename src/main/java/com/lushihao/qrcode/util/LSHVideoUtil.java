package com.lushihao.qrcode.util;

import org.jim2mov.core.*;
import org.jim2mov.utils.MovieUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 视频处理类
 */
public class LSHVideoUtil {

    /**
     * 图片转视频
     *
     * @param map
     * @param mp4FileName
     * @param fps
     * @param mWidth
     * @param mHeight
     * @return
     */
    public static boolean jpg2Mp4(Map<Integer, BufferedImage> map, String mp4FileName, int fps, int mWidth, int mHeight) {
        if (map == null || map.size() == 0) {
            return false;
        }
        File mp4File = new File(mp4FileName);
        if (mp4File.exists()) {
            mp4File.delete();
        }
        // 生成视频的名称
        DefaultMovieInfoProvider dmip = new DefaultMovieInfoProvider(mp4FileName.substring(mp4FileName.lastIndexOf("/") + 1));
        dmip.setMediaLocator("file:///" + mp4FileName);
        // 设置每秒帧数
        dmip.setFPS(fps > 0 ? fps : 3); // 如果未设置，默认为3
        // 设置总帧数
        dmip.setNumberOfFrames(map.size());
        // 设置视频宽和高（最好与图片宽高保持一直）
        dmip.setMWidth(mWidth > 0 ? mWidth : 1080); // 如果未设置，默认为1080
        dmip.setMHeight(mHeight > 0 ? mHeight : 2280); // 如果未设置，默认为2280

        try {
            new Jim2Mov(new ImageProvider() {
                public byte[] getImage(int frame) {
                    try {
                        return MovieUtils.bufferedImageToJPEG(map.get(frame), 1.0f);
                    } catch (IOException e) {
                    }
                    return null;
                }
            }, dmip, null).saveMovie(MovieInfoProvider.TYPE_AVI_MJPEG);
        } catch (MovieSaveException e) {
            return false;
        }
        return true;
    }

}
