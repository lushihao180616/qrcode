package com.lushihao.qrcode.entity.video;

public class VideoCut {

    /**
     * 原视频路径
     */
    private String path;
    /**
     * 生成视频路径
     */
    private String newPath;
    /**
     * 开始时间
     */
    private int start;
    /**
     * 结束时间
     */
    private int end;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
