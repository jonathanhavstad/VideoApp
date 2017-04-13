package com.example.videoapp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoapp.R;
import com.example.videoapp.models.bg.tasks.RestTask;
import com.example.videoapp.models.data.Category;
import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.views.adapters.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoListFragment extends Fragment {
    private static final String BASE_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/";
    private static final String REST_API_URL = "gtv-videos-bucket/CastVideos/f.json";

    private OnItemSelected onItemSelected;
    private List<VideoData> videoDataList = new ArrayList<>();
    private RestTask restTask;
    private VideoListAdapter videoListAdapter;
    private boolean restTaskValid = false;
    private String dashUrl;

    @BindView(R.id.video_list)
    RecyclerView videoList;

    public interface OnItemSelected {
        void itemSelected(String dashUrl, VideoData videoData, int position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        ButterKnife.bind(this, view);
        videoListAdapter = new VideoListAdapter(videoDataList, new VideoListAdapter.OnItemSelected() {
            @Override
            public void itemSelected(VideoData videoData, int position) {
                onItemSelected.itemSelected(dashUrl, videoData, position);
            }
        },
                BASE_URL);
        videoList.setAdapter(videoListAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof OnItemSelected) {
            onItemSelected = (OnItemSelected) context;
        } else {
            throw new RuntimeException("Attached context must implement "
                    + OnItemSelected.class.getCanonicalName());
        }
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        restTaskValid = true;
        restTask = new RestTask(new RestTask.OnFinished() {
            @Override
            public void finished(Category category) {
                if (restTaskValid) {
                    dashUrl = category.getDashUrl();
                    videoDataList.clear();
                    videoDataList.addAll(category.getVideoDataList());
                    videoListAdapter.notifyDataSetChanged();
                }
            }
        });

        restTask.execute(REST_API_URL);
    }

    @Override
    public void onStop() {
        super.onStop();
        restTask.cancel(true);
        restTaskValid = false;
    }
}
