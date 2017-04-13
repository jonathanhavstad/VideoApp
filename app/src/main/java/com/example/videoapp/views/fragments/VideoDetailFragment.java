package com.example.videoapp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.presenters.AppPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoDetailFragment extends Fragment {
    public interface OnPlayVideo {
        void play(String dashUrl, VideoData videoData, AppPresenter.PLAYER_TYPE playerType);
    }

    @BindView(R.id.video_title)
    TextView videoTitle;
    @BindView(R.id.video_thumbnail)
    ImageView videoThumbnail;
    @BindView(R.id.video_duration)
    TextView videoDuration;
    @BindView(R.id.play_button)
    Button playButton;

    private String dashUrl;
    private VideoData videoData;
    private AppPresenter.PLAYER_TYPE playerType;

    private OnPlayVideo onPlayVideo;

    public static VideoDetailFragment newInstance(Context context,
                                                  String dashUrl,
                                                  VideoData videoData,
                                                  AppPresenter.PLAYER_TYPE playerType) {
        VideoDetailFragment videoDetailFragment = new VideoDetailFragment();
        Bundle args = new Bundle();
        args.putString(context.getString(R.string.dash_url_key), dashUrl);
        args.putParcelable(context.getString(R.string.video_data_key), videoData);
        args.putString(context.getString(R.string.player_type_key), playerType.toString());
        videoDetailFragment.setArguments(args);
        return videoDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        dashUrl = args.getString(getString(R.string.dash_url_key));
        videoData = args.getParcelable(getString(R.string.video_data_key));
        playerType = AppPresenter.PLAYER_TYPE.valueOf(args.getString(getString(R.string.player_type_key)));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);
        ButterKnife.bind(this, view);
        videoTitle.setText(videoData.getTitle());
        Picasso.with(view.getContext()).load(videoData.getImage_780_1200()).into(videoThumbnail);
        videoDuration.setText(videoData.getDuration());
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayVideo.play(dashUrl, videoData, playerType);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof OnPlayVideo) {
            onPlayVideo = (OnPlayVideo) context;
        } else {
            throw new RuntimeException("Attached context must implement " +
                    OnPlayVideo.class.getCanonicalName());
        }
        super.onAttach(context);
    }
}
