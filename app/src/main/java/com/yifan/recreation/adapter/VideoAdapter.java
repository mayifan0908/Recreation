package com.yifan.recreation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yifan.recreation.R;
import com.yifan.recreation.activity.PlayVideoActivity;
import com.yifan.recreation.bean.VideoBean;
import com.yifan.recreation.refreshview.recyclerview.BaseRecyclerAdapter;
import com.yifan.recreation.util.FrescoAttributeUtil;
import com.yifan.recreation.util.FrescoUtil;

import java.util.List;

/**
 * Author： fanyafeng
 * Data： 16/12/19 下午5:51
 * Email: fanyafeng@live.cn
 */
public class VideoAdapter extends BaseRecyclerAdapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private List<VideoBean> videoBeanList;

    public VideoAdapter(Context context, List<VideoBean> videoBeanList) {
        this.context = context;
        this.videoBeanList = videoBeanList;
    }

    @Override
    public VideoViewHolder getViewHolder(View view) {
        return new VideoViewHolder(view);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yinyue_layout, parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position, boolean isItem) {
        final VideoBean videoBean = videoBeanList.get(position);
        FrescoUtil.loadPicOnNet(holder.sdvImg, videoBean.getImg());
        holder.tvTitle.setText(videoBean.getTitle());
        FrescoUtil.loadPicOnNet(holder.sdvIcon, videoBean.getHeaderImg());
        holder.sdvIcon.setHierarchy(FrescoAttributeUtil.setCircleRingHierarchy(context, Color.BLUE, 5f));
        holder.sdvIcon.bringToFront();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayVideoActivity.class);
                intent.putExtra("videoUrl", videoBean.getVideoUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getAdapterItemCount() {
        return videoBeanList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdvImg;
        TextView tvTitle;
        SimpleDraweeView sdvIcon;

        public VideoViewHolder(View itemView) {
            super(itemView);
            sdvImg = (SimpleDraweeView) itemView.findViewById(R.id.sdvImg);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            sdvIcon = (SimpleDraweeView) itemView.findViewById(R.id.sdvIcon);
        }
    }
}
