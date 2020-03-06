package com.lushihao.qrcode.entity.video;

public class VideoCut {

    /**
     * 路径
     */
    private String path;
    /**
     * 开始时间
     */
    private double start;
    /**
     * 结束时间
     */
    private double end;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public VideoCut() {
    }

    public VideoCut(String path, double start, double end) {
        this.path = path;
        this.start = start;
        this.end = end;
    }

}
