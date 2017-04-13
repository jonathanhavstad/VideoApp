package com.example.videoapp.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.videoapp.R;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoPlayerActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
    }
}
