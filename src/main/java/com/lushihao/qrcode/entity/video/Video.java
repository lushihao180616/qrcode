package com.lushihao.qrcode.entity.video;

/**
 * 视频类
 */
public class Video {

    /**
     * 商家标识
     */
    private String businessCode;
    /**
     * 视频结束部分地址
     */
    private String endPath;
    /**
     * 原视频地址
     */
    private String oldVideoPath;
    /**
     * 生成视频地址
     */
    private String newVideoPath;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getEndPath() {
        return endPath;
    }

    public void setEndPath(String endPath) {
        this.endPath = endPath;
    }

    public String getOldVideoPath() {
        return oldVideoPath;
    }

    public void setOldVideoPath(String oldVideoPath) {
        this.oldVideoPath = oldVideoPath;
    }

    public String getNewVideoPath() {
        return newVideoPath;
    }

    public void setNewVideoPath(String newVideoPath) {
        this.newVideoPath = newVideoPath;
    }

    public Video() {
    }

    public Video(String businessCode, String endPath, String oldVideoPath, String newVideoPath) {
        this.businessCode = businessCode;
        this.endPath = endPath;
        this.oldVideoPath = oldVideoPath;
        this.newVideoPath = newVideoPath;
    }
}
