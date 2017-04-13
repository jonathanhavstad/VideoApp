package com.example.videoapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.presenters.AppPresenter;
import com.example.videoapp.views.fragments.ExoPlayerFragment;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoPlayerActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Intent intent = getIntent();
        String dashUrl = intent.getStringExtra(getString(R.string.dash_url_key));
        VideoData videoData = intent.getParcelableExtra(getString(R.string.video_data_key));
        AppPresenter.PLAYER_TYPE playerType =
                AppPresenter.PLAYER_TYPE.valueOf(intent.getStringExtra(getString(R.string.player_type_key)));
        switch (playerType) {
            case EXO_PLAYER:
                ExoPlayerFragment exoPlayerFragment = ExoPlayerFragment.newInstance(this, dashUrl, videoData);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.video_player_view, exoPlayerFragment)
                        .commit();
                break;
            case JW_PLAYER:
                break;
            default:
        }
    }
}
