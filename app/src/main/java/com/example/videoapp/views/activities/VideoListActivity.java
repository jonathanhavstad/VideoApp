package com.example.videoapp.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.presenters.AppPresenter;
import com.example.videoapp.views.fragments.VideoListFragment;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoListActivity extends FragmentActivity implements VideoListFragment.OnItemSelected {
    private static final int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
    }

    @Override
    public void itemSelected(String dashUrl, VideoData videoData, int position) {
        AppPresenter.PLAYER_TYPE playerType = AppPresenter.PLAYER_TYPE.EXO_PLAYER;
        if (position % 2 == 1) {
            playerType = AppPresenter.PLAYER_TYPE.JW_PLAYER;
        }
        AppPresenter.getAppPresenter().launchVideoDetailsActivity(this,
                REQUEST_CODE,
                dashUrl,
                videoData,
                playerType);
    }
}
