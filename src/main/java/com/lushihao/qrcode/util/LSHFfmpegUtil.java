package com.lushihao.qrcode.util;

import com.lushihao.qrcode.entity.video.VideoCut;
import com.lushihao.qrcode.entity.video.VideoFont;
import com.lushihao.qrcode.entity.video.VideoInfo;
import com.lushihao.qrcode.entity.video.VideoWaterMark;
import com.lushihao.qrcode.entity.yml.ProjectBasicInfo;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@Component
public class LSHFfmpegUtil {

    @Resource
    private ProjectBasicInfo projectBasicInfo;
    /**
     * ffmpeg安装目录
     */
    public String FFMPEG_PATH;
    /**
     * 视频
     */
    public static final int VIDEO = 0;
    /**
     * 图片
     */
    public static final int IMAGE = 1;
    /**
     * 不是视频和图片
     */
    public static final int NONE = 9;

    /**
     * 获取视频信息
     *
     * @return
     */
    public VideoInfo getVideoInfo(String videoPath) {
        FileInputStream fileInputStream = null;
        try {
            File file = new File(videoPath);
            Encoder encoder = new Encoder();
            MultimediaInfo mmi = encoder.getInfo(file);

            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setVideoLong(mmi.getDuration());
            videoInfo.setWidth(mmi.getVideo().getSize().getWidth());
            videoInfo.setHeight(mmi.getVideo().getSize().getHeight());
            videoInfo.setFormat(mmi.getFormat());
            fileInputStream = new FileInputStream(file);
            videoInfo.setSize(fileInputStream.getChannel().size());
            return videoInfo;
        } catch (EncoderException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 判断文件是否能被ffmpeg解析
     *
     * @param fileName
     * @return
     */
    public int checkFileType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
                .toLowerCase();
        if (type.equals("avi")) {
            return VIDEO;
        } else if (type.equals("mov")) {
            return VIDEO;
        } else if (type.equals("mp4")) {
            return VIDEO;
        } else if (type.equals("flv")) {
            return VIDEO;
        } else if (type.equals("png")) {
            return IMAGE;
        } else if (type.equals("jpg")) {
            return IMAGE;
        } else if (type.equals("jpeg")) {
            return IMAGE;
        }
        return NONE;
    }

    /**
     * 视频加水印
     *
     * @param videoWaterMark
     * @return
     */
    public boolean videoWaterMark(VideoWaterMark videoWaterMark) {
        File file = new File(videoWaterMark.getNewVideoPath());
        if (file.exists()) {
            file.delete();
        }
        String name;
        if (videoWaterMark.getBusiness() != null) {
            name = videoWaterMark.getBusiness().getName();
        } else {
            name = videoWaterMark.getManager().getName();
        }

        FFMPEG_PATH = projectBasicInfo.getFfmpegUrl();
        List<String> commands = new java.util.ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(videoWaterMark.getOldVideoPath());
        commands.add("-vf");
        commands.add("\"drawtext=fontfile=simhei.ttf:text=\"" + name + "\":x=" + videoWaterMark.getFontX() + ":y=" + videoWaterMark.getFontY() + ":fontsize=" + videoWaterMark.getFontSize() + ":fontcolor=" + videoWaterMark.getFontColor() + ":shadowy=" + videoWaterMark.getFontShadow() + "\"");
        commands.add(videoWaterMark.getNewVideoPath());

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commands);
        try {
            Process process = builder.start();
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = br.readLine()) != null) {
            }
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 视频截取
     *
     * @param videoCut
     * @return
     */
    public boolean videoCut(VideoCut videoCut) {
        File file = new File(videoCut.getNewPath());
        if (file.exists()) {
            file.delete();
        }
        int startMinute = (int) (videoCut.getStart() / 60);
        int startSecond = (int) (videoCut.getStart() % 60);
        int minute = (int) ((videoCut.getEnd() - videoCut.getStart()) / 60);
        int second = (int) ((videoCut.getEnd() - videoCut.getStart()) % 60);
        FFMPEG_PATH = projectBasicInfo.getFfmpegUrl();
        List<String> commands = new java.util.ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-ss");
        commands.add("0:" + startMinute + ":" + startSecond);
        commands.add("-t");
        commands.add("0:" + minute + ":" + second);
        commands.add("-i");
        commands.add(videoCut.getPath());
        commands.add("-vcodec");
        commands.add("copy");
        commands.add("-acodec");
        commands.add("copy");
        commands.add(videoCut.getNewPath());

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commands);
        try {
            Process process = builder.start();
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = br.readLine()) != null) {
            }
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 视频截取
     *
     * @param videoFont
     * @return
     */
    public boolean videoFont(VideoFont videoFont) {
        File file = new File(videoFont.getNewPath());
        if (file.exists()) {
            file.delete();
        }
        FFMPEG_PATH = projectBasicInfo.getFfmpegUrl();
        List<String> commands = new java.util.ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(videoFont.getPath());
        commands.add("-i");
        commands.add(videoFont.getImagePath());
        commands.add("-filter_complex");
        commands.add("\"overlay=x=0:y=0\"");
        commands.add(videoFont.getNewPath());

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commands);
        try {
            Process process = builder.start();
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = br.readLine()) != null) {
            }
            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}