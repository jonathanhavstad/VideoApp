package com.example.videoapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.presenters.AppPresenter;
import com.example.videoapp.views.fragments.VideoDetailFragment;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoDetailsActivity extends FragmentActivity implements VideoDetailFragment.OnPlayVideo {
    private static final int REQUEST_CODE = 1002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        Intent intent = getIntent();
        String dashUrl = intent.getStringExtra(getString(R.string.dash_url_key));
        VideoData videoData = intent.getParcelableExtra(getString(R.string.video_data_key));
        AppPresenter.PLAYER_TYPE playerType =
                AppPresenter.PLAYER_TYPE.valueOf(intent.getStringExtra(getString(R.string.player_type_key)));
        VideoDetailFragment videoDetailFragment = VideoDetailFragment.newInstance(this,
                dashUrl,
                videoData,
                playerType);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.video_detail_view, videoDetailFragment)
                .commit();
    }

    @Override
    public void play(String dashUrl, VideoData videoData, AppPresenter.PLAYER_TYPE playerType) {
        AppPresenter.getAppPresenter()
                .launchVideoPlayerActivity(this,
                        REQUEST_CODE,
                        dashUrl,
                        videoData,
                        playerType);
    }
}
