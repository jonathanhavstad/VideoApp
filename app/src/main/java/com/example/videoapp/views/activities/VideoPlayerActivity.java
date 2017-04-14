package com.example.videoapp.views.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.models.data.VideoSource;
import com.example.videoapp.presenters.AppPresenter;
import com.example.videoapp.views.fragments.ExoPlayerFragment;
import com.longtailvideo.jwplayer.JWPlayerFragment;
import com.longtailvideo.jwplayer.JWPlayerSupportFragment;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;

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
        Fragment playerFrament = null;
        switch (playerType) {
            case EXO_PLAYER:
                playerFrament = ExoPlayerFragment.newInstance(this, dashUrl, videoData);
                Toast.makeText(this, R.string.exo_player_toast_message, Toast.LENGTH_SHORT).show();
                break;
            case JW_PLAYER:
                StringBuffer videoUri = new StringBuffer();
                videoUri.append(dashUrl);
                for (VideoSource videoSource : videoData.getSources()) {
                    if (videoSource.getType().equals(getString(R.string.dash_video_source_key))) {
                        videoUri.append(videoSource.getUrl());
                    }
                }
                PlayerConfig playerConfig =
                        new PlayerConfig.Builder().file(videoUri.toString()).build();
                playerFrament = JWPlayerSupportFragment.newInstance(playerConfig);
                Toast.makeText(this, R.string.jw_player_toast_message, Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        if (playerFrament != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.video_player_view, playerFrament)
                    .commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }
}
