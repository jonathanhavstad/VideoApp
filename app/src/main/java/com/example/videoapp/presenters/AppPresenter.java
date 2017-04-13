package com.example.videoapp.presenters;

import android.app.Activity;
import android.content.Intent;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.views.activities.VideoDetailsActivity;
import com.example.videoapp.views.activities.VideoListActivity;
import com.example.videoapp.views.activities.VideoPlayerActivity;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class AppPresenter {
    private static AppPresenter appPresenter;

    public enum PLAYER_TYPE {
        EXO_PLAYER,
        JW_PLAYER
    }

    private AppPresenter() {

    }

    public static AppPresenter getAppPresenter() {
        if (appPresenter == null) {
            appPresenter = new AppPresenter();
        }
        return appPresenter;
    }

    public void launchVideoListActivity(Activity activity, int requestCode) {
        Intent videoListIntent = new Intent(activity, VideoListActivity.class);
        activity.startActivityForResult(videoListIntent, requestCode);
    }

    public void launchVideoDetailsActivity(Activity activity,
                                           int requestCode,
                                           String imagesUrl,
                                           String dashUrl,
                                           VideoData videoData,
                                           PLAYER_TYPE playerType) {
        Intent videoDetailsIntent = new Intent(activity, VideoDetailsActivity.class);
        videoDetailsIntent.putExtra(activity.getString(R.string.images_url_key), imagesUrl);
        videoDetailsIntent.putExtra(activity.getString(R.string.dash_url_key), dashUrl);
        videoDetailsIntent.putExtra(activity.getString(R.string.video_data_key), videoData);
        videoDetailsIntent.putExtra(activity.getString(R.string.player_type_key), playerType.toString());
        activity.startActivityForResult(videoDetailsIntent, requestCode);
    }

    public void launchVideoPlayerActivity(Activity activity,
                                          int requestCode,
                                          String dashUrl,
                                          VideoData videoData,
                                          PLAYER_TYPE playerType) {
        Intent videoPlayerIntent = new Intent(activity, VideoPlayerActivity.class);
        videoPlayerIntent.putExtra(activity.getString(R.string.dash_url_key), dashUrl);
        videoPlayerIntent.putExtra(activity.getString(R.string.video_data_key), videoData);
        videoPlayerIntent.putExtra(activity.getString(R.string.player_type_key), playerType.toString());
        activity.startActivityForResult(videoPlayerIntent, requestCode);
    }
}
