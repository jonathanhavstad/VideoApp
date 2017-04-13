package com.example.videoapp.models.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoTrack implements Parcelable {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("subtype")
    private String subtype;
    @Expose
    @SerializedName("contentId")
    private String contentId;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("language")
    private String language;

    protected VideoTrack(Parcel in) {
        id = in.readString();
        type = in.readString();
        subtype = in.readString();
        contentId = in.readString();
        name = in.readString();
        language = in.readString();
    }

    public static final Creator<VideoTrack> CREATOR = new Creator<VideoTrack>() {
        @Override
        public VideoTrack createFromParcel(Parcel in) {
            return new VideoTrack(in);
        }

        @Override
        public VideoTrack[] newArray(int size) {
            return new VideoTrack[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(subtype);
        dest.writeString(contentId);
        dest.writeString(name);
        dest.writeString(language);
    }
}
