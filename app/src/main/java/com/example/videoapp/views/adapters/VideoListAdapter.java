package com.example.videoapp.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.videoapp.R;
import com.example.videoapp.models.data.VideoData;
import static com.example.videoapp.models.utils.TimeUtils.formatTimeFromSeconds;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    public interface OnItemSelected {
        void itemSelected(VideoData videoData, int position);
    }

    private final List<VideoData> videoDataList;
    private final OnItemSelected onItemSelected;
    private String imagesUrl;

    public VideoListAdapter(List<VideoData> videoDataList,
                            OnItemSelected onItemSelected) {
        this.videoDataList = videoDataList;
        this.onItemSelected = onItemSelected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.viewholder_video_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final VideoData videoData = videoDataList.get(position);
        holder.listItemVideoTitle.setText(videoData.getTitle());
        StringBuffer imageFullUrl = new StringBuffer();
        imageFullUrl.append(imagesUrl);
        imageFullUrl.append(videoData.getImage_480_270());
        Picasso
                .with(holder.itemView.getContext())
                .load(imageFullUrl.toString())
                .into(holder.listItemImageView);
        holder.listItemVideoDuration.setText(formatTimeFromSeconds(Long.valueOf(videoData.getDuration())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected.itemSelected(videoData, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        @BindView(R.id.listitem_video_title)
        TextView listItemVideoTitle;
        @BindView(R.id.listitem_video_thumbnail)
        ImageView listItemImageView;
        @BindView(R.id.listitem_video_duration)
        TextView listItemVideoDuration;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
