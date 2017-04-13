package com.example.videoapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.models.data.VideoSource;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class ExoPlayerFragment extends Fragment {
    private SimpleExoPlayer simpleExoPlayer;
    private String dashUrl;
    private VideoData videoData;

    @BindView(R.id.exoplayer_view)
    SimpleExoPlayerView simpleExoPlayerView;

    public static ExoPlayerFragment newInstance(Context context, String dashUrl, VideoData videoData) {
        Bundle args = new Bundle();
        args.putString(context.getString(R.string.dash_url_key), dashUrl);
        args.putParcelable(context.getString(R.string.video_data_key), videoData);
        ExoPlayerFragment fragment = new ExoPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        dashUrl = args.getString(getString(R.string.dash_url_key));
        videoData = args.getParcelable(getString(R.string.video_data_key));

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "VideoApp"));
        StringBuffer videoUri = new StringBuffer();
        videoUri.append(dashUrl);
        for (VideoSource videoSource : videoData.getSources()) {
            if (videoSource.getType().equals(getString(R.string.dash_video_source_key))) {
                videoUri.append(videoSource.getUrl());
            }
        }
        DashChunkSource.Factory dashChunkSourceFactory =
                new DefaultDashChunkSource.Factory(dataSourceFactory);
        MediaSource videoSource = new DashMediaSource(Uri.parse(videoUri.toString()),
                dataSourceFactory,
                dashChunkSourceFactory,
                null,
                null);
        simpleExoPlayer.prepare(videoSource);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exoplayer, container, false);
        ButterKnife.bind(this, view);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}
