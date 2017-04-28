package com.example.videoapp;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.models.data.VideoSource;
import com.example.videoapp.presenters.AppPresenter;

import java.util.Arrays;

public class LaunchActivity extends AppCompatActivity {
    private static final int REQEUST_CODE = 1000;

    private static final String MOCK_VIDEO_TITLE = "Designing For Google Cast";
    private static final String MOCK_VIDEO_DURATION = "333";
    private static final String MOCK_VIDEO_480_270 = "480x270/DesigningForGoogleCast2-480x270.jpg";
    private static final String MOCK_VIDEO_780_1200 = "780x1200/DesigningForGoogleCast-887x1200.jpg";
    private static final String MOCK_VIDEO_STUDIO = "Google IO - 2014";
    private static final String MOCK_VIDEO_SUBTITLE = "Fusce id nisi turpis. Praesent viverra bibendum semper. Donec tristique, orci sed semper lacinia, quam erat rhoncus massa, non congue tellus est quis tellus. Sed mollis orci venenatis quam scelerisque accumsan. Curabitur a massa sit amet mi accumsan mollis sed et magna. Vivamus sed aliquam risus. Nulla eget dolor in elit facilisis mattis. Ut aliquet luctus lacus. Phasellus nec commodo erat. Praesent tempus id lectus ac scelerisque. Maecenas pretium cursus lectus id volutpat.";
    private static final String MOCK_VIDEO_SOURCE_URL = "";
    private static final String MOCK_IMAGES_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/";
    private static final String MOCK_DASH_URL = "http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8";

    private VideoSource videoSource;
    private VideoData videoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //AppPresenter.getAppPresenter().launchVideoListActivity(this, REQEUST_CODE);

        videoSource = VideoSource.CREATOR.createFromParcel(Parcel.obtain());
        videoSource.setType(getString(R.string.dash_video_source_key));
        videoSource.setUrl(MOCK_VIDEO_SOURCE_URL);

        videoData = VideoData.CREATOR.createFromParcel(Parcel.obtain());
        videoData.setSubtitle(MOCK_VIDEO_SUBTITLE);
        videoData.setSources(Arrays.asList(videoSource));
        videoData.setImage_480_270(MOCK_VIDEO_480_270);
        videoData.setImage_780_1200(MOCK_VIDEO_780_1200);
        videoData.setTitle(MOCK_VIDEO_TITLE);
        videoData.setStudio(MOCK_VIDEO_STUDIO);
        videoData.setDuration(MOCK_VIDEO_DURATION);

        AppPresenter.getAppPresenter().launchVideoPlayerActivity(this,
                1002,
                MOCK_DASH_URL,
                videoData,
                AppPresenter.PLAYER_TYPE.EXO_PLAYER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQEUST_CODE) {
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
