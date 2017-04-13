package com.example.videoapp.models.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoData implements Parcelable {
    @Expose
    @SerializedName("subtitle")
    private String subtitle;
    @Expose
    @SerializedName("sources")
    private List<VideoSource> sources;
    @Expose
    @SerializedName("thumb")
    private String thumb;
    @Expose
    @SerializedName("image-480x270")
    private String image_480_270;
    @Expose
    @SerializedName("image-780x1200")
    private String image_780_1200;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("studio")
    private String studio;
    @Expose
    @SerializedName("duration")
    private String duration;
    @Expose
    @SerializedName("tracks")
    private List<VideoTrack> tracks;

    protected VideoData(Parcel in) {
        subtitle = in.readString();
        sources = in.createTypedArrayList(VideoSource.CREATOR);
        thumb = in.readString();
        image_480_270 = in.readString();
        image_780_1200 = in.readString();
        title = in.readString();
        studio = in.readString();
        duration = in.readString();
        tracks = in.createTypedArrayList(VideoTrack.CREATOR);
    }

    public static final Creator<VideoData> CREATOR = new Creator<VideoData>() {
        @Override
        public VideoData createFromParcel(Parcel in) {
            return new VideoData(in);
        }

        @Override
        public VideoData[] newArray(int size) {
            return new VideoData[size];
        }
    };

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<VideoSource> getSources() {
        return sources;
    }

    public void setSources(List<VideoSource> sources) {
        this.sources = sources;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage_480_270() {
        return image_480_270;
    }

    public void setImage_480_270(String image_480_270) {
        this.image_480_270 = image_480_270;
    }

    public String getImage_780_1200() {
        return image_780_1200;
    }

    public void setImage_780_1200(String image_780_1200) {
        this.image_780_1200 = image_780_1200;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<VideoTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<VideoTrack> tracks) {
        this.tracks = tracks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subtitle);
        dest.writeTypedList(sources);
        dest.writeString(thumb);
        dest.writeString(image_480_270);
        dest.writeString(image_780_1200);
        dest.writeString(title);
        dest.writeString(studio);
        dest.writeString(duration);
        dest.writeTypedList(tracks);
    }
}
