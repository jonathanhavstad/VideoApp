package com.example.videoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.videoapp.presenters.AppPresenter;

public class LaunchActivity extends AppCompatActivity {
    private static final int REQEUST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        AppPresenter.getAppPresenter().launchVideoListActivity(this, REQEUST_CODE);
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
