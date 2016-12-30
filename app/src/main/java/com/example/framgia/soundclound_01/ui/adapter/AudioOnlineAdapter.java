package com.example.framgia.soundclound_01.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.data.model.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioOnlineAdapter
    extends RecyclerView.Adapter<AudioOnlineAdapter.MyViewHolder> {
    private static final String FORMAT_NUMBER = "%1$,.0f";
    private List<Track> mListTracks;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public AudioOnlineAdapter(List<Track> list, Context context) {
        mListTracks = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
            mLayoutInflater.inflate(R.layout.item_recycleview_audio_online, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(mListTracks.get(position));
    }

    @Override
    public int getItemCount() {
        return mListTracks != null ? mListTracks.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_audio_title)
        TextView mAudioTitle;
        @BindView(R.id.text_user_name)
        TextView mUserName;
        @BindView(R.id.text_playback_count)
        TextView mPlaybackCount;
        @BindView(R.id.image_audio_icon)
        ImageView mImageAudioIcon;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(Track track) {
            if (track == null || track.getUser() == null) return;
            mAudioTitle.setText(track.getTitle());
            mUserName.setText(track.getUser().getUserName());
            mPlaybackCount.setText(String.format(FORMAT_NUMBER, track.getPlaybackCount()));
            Picasso.with(mContext)
                .load(track.getArtworkUrl())
                .into(mImageAudioIcon);
        }
    }
}
