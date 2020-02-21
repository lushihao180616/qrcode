package com.lushihao.qrcode.entity.video;

public class VideoInfo {

    /**
     * 视频时长（毫秒）
     */
    private long videoLong;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 格式
     */
    private String format;
    /**
     * 文件大小（B）
     */
    private long size;

    public long getVideoLong() {
        return videoLong;
    }

    public void setVideoLong(long videoLong) {
        this.videoLong = videoLong;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
