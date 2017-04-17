package com.example.videoapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.models.data.VideoSource;
import com.example.videoapp.presenters.AppPresenter;
import com.example.videoapp.views.fragments.ExoPlayerFragment;
import com.longtailvideo.jwplayer.JWPlayerSupportFragment;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoPlayerActivity extends FragmentActivity {
    private static final String TAG = "VideoPlayerActivity";
    private static final String EXO_PLAYER_FRAGMENT_TAG = "ExoPlayerFragment";
    private static final String JW_PLAYER_FRAGMENT_TAG = "JwPlayerFragment";

    private String fragmentTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_video_player);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String dashUrl = intent.getStringExtra(getString(R.string.dash_url_key));
            VideoData videoData = intent.getParcelableExtra(getString(R.string.video_data_key));
            AppPresenter.PLAYER_TYPE playerType =
                    AppPresenter.PLAYER_TYPE.valueOf(intent.getStringExtra(getString(R.string.player_type_key)));
            Fragment playerFragment = null;
            boolean createFragment = false;
            fragmentTag = null;
            switch (playerType) {
                case EXO_PLAYER:
                    fragmentTag = EXO_PLAYER_FRAGMENT_TAG;
                    if (getSupportFragmentManager().findFragmentByTag(fragmentTag) == null) {
                        playerFragment = ExoPlayerFragment.newInstance(this, dashUrl, videoData);
                        createFragment = true;
                    }
                    Toast.makeText(this, R.string.exo_player_toast_message, Toast.LENGTH_SHORT).show();
                    break;
                case JW_PLAYER:
                    fragmentTag = JW_PLAYER_FRAGMENT_TAG;
                    if (getSupportFragmentManager().findFragmentByTag(fragmentTag) == null) {
                        StringBuffer videoUri = new StringBuffer();
                        videoUri.append(dashUrl);
                        for (VideoSource videoSource : videoData.getSources()) {
                            if (videoSource.getType().equals(getString(R.string.dash_video_source_key))) {
                                videoUri.append(videoSource.getUrl());
                            }
                        }
                        PlayerConfig playerConfig =
                                new PlayerConfig.Builder().file(videoUri.toString()).build();
                        playerFragment = JWPlayerSupportFragment.newInstance(playerConfig);
                        createFragment = true;
                    }
                    Toast.makeText(this, R.string.jw_player_toast_message, Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
            if (createFragment) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.video_player_view, playerFragment, fragmentTag)
                        .commit();
            }
        } else {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (isFinishing()) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment currentFragment = fm.findFragmentByTag(fragmentTag);
            if (currentFragment != null) {
                fm.beginTransaction().remove(currentFragment).commit();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
