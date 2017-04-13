package com.example.videoapp.models.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoSource implements Parcelable {
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("mime")
    private String mime;
    @Expose
    @SerializedName("url")
    private String url;

    protected VideoSource(Parcel in) {
        type = in.readString();
        mime = in.readString();
        url = in.readString();
    }

    public static final Creator<VideoSource> CREATOR = new Creator<VideoSource>() {
        @Override
        public VideoSource createFromParcel(Parcel in) {
            return new VideoSource(in);
        }

        @Override
        public VideoSource[] newArray(int size) {
            return new VideoSource[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(mime);
        dest.writeString(url);
    }
}
