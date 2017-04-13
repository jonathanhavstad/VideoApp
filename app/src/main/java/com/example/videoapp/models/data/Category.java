package com.example.videoapp.models.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class Category {
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("hls")
    private String hlsUrl;
    @Expose()
    @SerializedName("dash")
    private String dashUrl;
    @Expose
    @SerializedName("mp4")
    private String mp4Url;
    @Expose
    @SerializedName("images")
    private String imagesUrl;
    @Expose
    @SerializedName("tracks")
    private String tracksUrl;
    @Expose
    @SerializedName("videos")
    private List<VideoData> videoDataList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public String getDashUrl() {
        return dashUrl;
    }

    public void setDashUrl(String dashUrl) {
        this.dashUrl = dashUrl;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getTracksUrl() {
        return tracksUrl;
    }

    public void setTracksUrl(String tracksUrl) {
        this.tracksUrl = tracksUrl;
    }

    public List<VideoData> getVideoDataList() {
        return videoDataList;
    }

    public void setVideoDataList(List<VideoData> videoDataList) {
        this.videoDataList = videoDataList;
    }
}
