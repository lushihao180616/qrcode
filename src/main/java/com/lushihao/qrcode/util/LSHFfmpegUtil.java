package com.lushihao.qrcode.util;

import com.lushihao.qrcode.entity.basic.ProjectBasicInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

@Component
public class LSHFfmpegUtil {

    @Resource
    private ProjectBasicInfo projectBasicInfo;

    /**
     * ffmpeg安装目录
     */
    public String FFMPEG_PATH;

    /**
     * 设置图片大小
     */
    private final static String IMG_SIZE = "1080x2280";

    /**
     * 视频合并，只保留第一个的音频
     *
     * @param videoPath1
     * @param videoPath2
     * @param outputPath
     * @return
     */
    public String videoAddVideo(String videoPath1, String videoPath2, String outputPath) {
        FFMPEG_PATH = projectBasicInfo.getFfmpegUrl();

        if (checkFileType(videoPath1) != 0 || checkFileType(videoPath2) != 0) {
            return "文件格式错误！！！";
        }
        String video1 = videoToTs(videoPath1);
        String video2 = videoToTs(videoPath2);
        List<String> commands = new java.util.ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add("concat:" + video1 + "|" + video2);
        commands.add("-c");
        commands.add("copy");
        commands.add(outputPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();
            System.out.println("合并成功:" + outputPath);

            BufferedReader buf = null; // 保存ffmpeg的输出结果流
            String line = null;

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                continue;
            }
            p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            new File(video1).delete();
            new File(video2).delete();
            return "生成成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "发生未知错误，请重启服务！！！";
        }
    }

    /**
     * 将video转换为ts格式，此格式能视频拼接
     *
     * @param videoPath1
     * @return
     */
    private String videoToTs(String videoPath1) {
        List<String> commands = new java.util.ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(videoPath1);
        commands.add("-c");
        commands.add("copy");
        commands.add("-bsf:v");
        commands.add("h264_mp4toannexb");
        commands.add("-f");
        commands.add("mpegts");
        String nowPath = videoPath1.substring(0, videoPath1.lastIndexOf("\\") + 1) + UUID.randomUUID().toString().replace("-", "") + ".ts";
        commands.add(nowPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();

            BufferedReader buf = null; // 保存ffmpeg的输出结果流
            String line = null;

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                continue;
            }
            p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            return nowPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断文件是否能被ffmpeg解析
     *
     * @param fileName
     * @return
     */
    private int checkFileType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
                .toLowerCase();
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        } else if (type.equals("png")) {
            return 1;
        } else if (type.equals("jpg")) {
            return 1;
        } else if (type.equals("jpeg")) {
            return 1;
        }
        return 9;
    }

//    /**
//     * 视频截图方法
//     *
//     * @param videoPath
//     * @param imagePath
//     * @param timePoint
//     * @return
//     */
//    public boolean ffmpegToImage(String videoPath, String imagePath, int timePoint) {
//        List<String> commands = new java.util.ArrayList<String>();
//        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
//        commands.add(FFMPEG_PATH);
//        commands.add("-ss");
//        commands.add(timePoint + "");//这个参数是设置截取视频多少秒时的画面
//        commands.add("-i");
//        commands.add(videoPath);
//        commands.add("-y");
//        commands.add("-f");
//        commands.add("image2");
//        commands.add("-t");
//        commands.add("0.001");
//        commands.add("-s");
//        commands.add(IMG_SIZE); //这个参数是设置截取图片的大小
//        commands.add(imagePath);
//        try {
//            ProcessBuilder builder = new ProcessBuilder();
//            builder.command(commands);
//            builder.start();
//            System.out.println("截取成功:" + imagePath);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 获取视频时长
//     *
//     * @param video_path
//     * @return
//     */
//    public int getVideoTime(String video_path) {
//        List<String> commands = new java.util.ArrayList<String>();
//        commands.add(FFMPEG_PATH);
//        commands.add("-i");
//        commands.add(video_path);
//        try {
//            ProcessBuilder builder = new ProcessBuilder();
//            builder.command(commands);
//            final Process p = builder.start();
//
//            //从输入流中读取视频信息
//            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//            StringBuffer sb = new StringBuffer();
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//            br.close();
//
//            //从视频信息中解析时长
//            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
//            Pattern pattern = Pattern.compile(regexDuration);
//            Matcher m = pattern.matcher(sb.toString());
//            if (m.find()) {
//                int time = getTimelen(m.group(1));
//                System.out.println(video_path + ",视频时长：" + time + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
//                return time;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return 0;
//    }
//
//    /**
//     * 视频时长格式
//     *
//     * @param timelen
//     * @return
//     */
//    private int getTimelen(String timelen) {
//        int min = 0;
//        String strs[] = timelen.split(":");
//        if (strs[0].compareTo("0") > 0) {
//            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
//        }
//        if (strs[1].compareTo("0") > 0) {
//            min += Integer.valueOf(strs[1]) * 60;
//        }
//        if (strs[2].compareTo("0") > 0) {
//            min += Math.round(Float.valueOf(strs[2]));
//        }
//        return min;
//    }
//
//    /**
//     * 视频抽取音频文件
//     *
//     * @param videoPath
//     * @param type
//     * @param audioPath
//     * @return
//     */
//    public boolean ffmpegToAudio(String videoPath, String type, String audioPath) {
//        List<String> commands = new java.util.ArrayList<String>();
//        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
//        commands.add(FFMPEG_PATH);
//        commands.add("-i");
//        commands.add(videoPath);
//        commands.add("-f");
//        commands.add(type);
//        commands.add("-vn");
//        commands.add("-y");
//        commands.add("-acodec");
//        if ("wav".equals(type)) {
//            commands.add("pcm_s16le");
//        } else if ("mp3".equals(type)) {
//            commands.add("mp3");
//        }
//        commands.add("-ar");
//        commands.add("16000");
//        commands.add("-ac");
//        commands.add("1");
//        commands.add(audioPath);
//        try {
//            ProcessBuilder builder = new ProcessBuilder();
//            builder.command(commands);
//            Process p = builder.start();
//            System.out.println("抽离成功:" + audioPath);
//
//            // 1. start
//            BufferedReader buf = null; // 保存ffmpeg的输出结果流
//            String line = null;
//
//            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//            StringBuffer sb = new StringBuffer();
//            while ((line = buf.readLine()) != null) {
//                System.out.println(line);
//                sb.append(line);
//                continue;
//            }
//            p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

}