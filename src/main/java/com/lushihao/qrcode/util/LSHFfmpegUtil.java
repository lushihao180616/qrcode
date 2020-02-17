package com.lushihao.qrcode.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LSHFfmpegUtil {

    /**
     * ffmpeg安装目录
     */
    public static String FFMPEG_PATH = "C:\\tempFile\\ffmpeg\\bin\\ffmpeg.exe";

    /**
     * 设置图片大小
     */
    private final static String IMG_SIZE = "1080x2280";

    /**
     * 视频截图方法
     *
     * @param videoPath
     * @param imagePath
     * @param timePoint
     * @return
     */
    public static boolean ffmpegToImage(String videoPath, String imagePath, int timePoint) {
        List<String> commands = new java.util.ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-ss");
        commands.add(timePoint + "");//这个参数是设置截取视频多少秒时的画面
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-t");
        commands.add("0.001");
        commands.add("-s");
        commands.add(IMG_SIZE); //这个参数是设置截取图片的大小
        commands.add(imagePath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            System.out.println("截取成功:" + imagePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否能被ffmpeg解析
     *
     * @param fileName
     * @return
     */
    public static int checkFileType(String fileName) {
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

    /**
     * 获取视频时长
     *
     * @param video_path
     * @return
     */
    static int getVideoTime(String video_path) {
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                int time = getTimelen(m.group(1));
                System.out.println(video_path + ",视频时长：" + time + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
                return time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 视频时长格式
     *
     * @param timelen
     * @return
     */
    private static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }

    /**
     * 秒转化成 hh:mm:ss
     *
     * @param duration
     * @return
     */
    public static String convertInt2Date(long duration) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(cal.getTimeInMillis() + duration * 1000);
    }

    /**
     * 视频抽取音频文件
     *
     * @param videoPath
     * @param type
     * @param audioPath
     * @return
     */
    public static boolean ffmpegToAudio(String videoPath, String type, String audioPath) {
        List<String> commands = new java.util.ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-f");
        commands.add(type);
        commands.add("-vn");
        commands.add("-y");
        commands.add("-acodec");
        if ("wav".equals(type)) {
            commands.add("pcm_s16le");
        } else if ("mp3".equals(type)) {
            commands.add("mp3");
        }
        commands.add("-ar");
        commands.add("16000");
        commands.add("-ac");
        commands.add("1");
        commands.add(audioPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();
            System.out.println("抽离成功:" + audioPath);

            // 1. start
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * wav 转 mp3
     *
     * @param wavPath
     * @param mp3Path
     * @return
     */
    public static boolean ffmpegOfwavTomp3(String wavPath, String mp3Path) {
        List<String> commands = new java.util.ArrayList<String>();
        FFMPEG_PATH = FFMPEG_PATH.replace("%20", " ");
        commands.add(FFMPEG_PATH);
        commands.add("-i");
        commands.add(wavPath);
        commands.add("-f");
        commands.add("mp3");
        commands.add("-acodec");
        commands.add("libmp3lame");
        commands.add("-y");
        commands.add(mp3Path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();
            System.out.println("转换成功:" + mp3Path);

            // 1. start
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
            // 1. end
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
//        // 视频文件
//        String videoRealPath1 = "D:\\5.MOV";
//        // 截图的路径（输出路径）
//        String imageRealPath1 = "D:\\31.jpg";
//        String imageRealPath2 = "D:\\32.jpg";
//        if(checkFileType(videoRealPath1) ==0){
//        	 ffmpegToImage(videoRealPath1,imageRealPath1,10);
//        	 ffmpegToImage(videoRealPath1,imageRealPath2,11);
//        }
//		String videoRealPath = "C:\\Users\\86153\\Desktop\\model.mp4";
//		String audioRealPath = "C:\\Users\\86153\\Desktop\\123.mp3";
//		String audioType = "mp3";
//		ffmpegToAudio(videoRealPath,audioType,audioRealPath);

        String videoRealPath = "C:\\Users\\86153\\Desktop\\model.mp4";
        String imagePath = "C:\\Users\\86153\\Desktop\\001.jpg";
        ffmpegToImage(videoRealPath, imagePath, 1);

//        String wavPath = "D:\\HHH\\model.mp4";
//        String mp3Path = "D:\\HHH\\123.mp3";
//        ffmpegOfwavTomp3(wavPath, mp3Path);
    }
}