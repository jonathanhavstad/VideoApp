package com.example.videoapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.models.data.VideoSource;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class ExoPlayerFragment extends Fragment {
    private static final String TAG = "ExoPlayerFragment";

    private SimpleExoPlayer simpleExoPlayer;
    private String dashUrl;
    private VideoData videoData;
    private SavedState savedState;

    public static class SavedState implements Parcelable {
        private boolean isPlaying;
        private int resumeWindow;
        private long resumePosition;

        protected SavedState(Parcel in) {
            isPlaying = in.readByte() != 0;
            resumeWindow = in.readInt();
            resumePosition = in.readLong();
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (isPlaying ? 1 : 0));
            dest.writeInt(resumeWindow);
            dest.writeLong(resumePosition);
        }
    }

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
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        Parcel parcel = Parcel.obtain();
        parcel.writeByte((byte) 0);
        parcel.writeInt(C.INDEX_UNSET);
        parcel.writeLong(C.TIME_UNSET);
        savedState = SavedState.CREATOR.createFromParcel(parcel);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        Bundle args = getArguments();
        dashUrl = args.getString(getString(R.string.dash_url_key));
        videoData = args.getParcelable(getString(R.string.video_data_key));

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_exoplayer, container, false);
        ButterKnife.bind(this, view);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

        simpleExoPlayerView.setPlayer(simpleExoPlayer);

        DataSource.Factory mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(),
                (DefaultBandwidthMeter) bandwidthMeter,
                new DefaultHttpDataSourceFactory(
                        Util.getUserAgent(getContext(), getString(R.string.video_app_name)),
                        (DefaultBandwidthMeter) bandwidthMeter));
        StringBuffer videoUri = new StringBuffer();
        videoUri.append(dashUrl);
        for (VideoSource videoSource : videoData.getSources()) {
            if (videoSource.getType().equals(getString(R.string.dash_video_source_key))) {
                videoUri.append(videoSource.getUrl());
            }
        }
//        DashChunkSource.Factory dashChunkSourceFactory =
//                new DefaultDashChunkSource.Factory(mediaDataSourceFactor);
//        MediaSource videoSource = new DashMediaSource(Uri.parse(videoUri.toString()),
//                dataSourceFactory,
//                dashChunkSourceFactory,
//                null,
//                null);
        MediaSource videoSource = new HlsMediaSource(Uri.parse(videoUri.toString()),
                mediaDataSourceFactory,
                null,
                null);
        simpleExoPlayer.prepare(videoSource);

        if (savedInstanceState != null) {
            savedState = savedInstanceState.getParcelable(getString(R.string.player_state_key));
            if (savedState != null) {
                simpleExoPlayer.setPlayWhenReady(savedState.isPlaying);
                if (savedState.resumeWindow != C.INDEX_UNSET) {
                    simpleExoPlayer.seekTo(savedState.resumeWindow, savedState.resumePosition);
                }
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        savedState.isPlaying = simpleExoPlayer.getPlayWhenReady();
        savedState.resumeWindow = simpleExoPlayer.getCurrentWindowIndex();
        savedState.resumePosition = simpleExoPlayer.isCurrentWindowSeekable() ?
                Math.max(0, simpleExoPlayer.getCurrentPosition()) : C.TIME_UNSET;
        outState.putParcelable(getString(R.string.player_state_key), savedState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
        simpleExoPlayer.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
}
