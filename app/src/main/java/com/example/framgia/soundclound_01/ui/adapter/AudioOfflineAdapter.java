package com.example.framgia.soundclound_01.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.data.model.Track;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioOfflineAdapter
    extends RecyclerView.Adapter<AudioOfflineAdapter.AudioOfflineViewHolder> {
    private List<Track> mListTracks;
    private LayoutInflater mLayoutInflater;
    private ClickListener mClickListener;

    public AudioOfflineAdapter(List<Track> list, Context context, ClickListener clickListener) {
        mListTracks = list;
        mLayoutInflater = LayoutInflater.from(context);
        mClickListener = clickListener;
    }

    @Override
    public AudioOfflineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
            mLayoutInflater.inflate(R.layout.item_recycleview_audio_offline, parent, false);
        return new AudioOfflineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AudioOfflineViewHolder holder, int position) {
        holder.bindData(mListTracks.get(position));
    }

    @Override
    public int getItemCount() {
        return mListTracks != null ? mListTracks.size() : 0;
    }

    public interface ClickListener {
        void setOnClickListener(int index);
    }

    public class AudioOfflineViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        @BindView(R.id.text_audio_title)
        TextView mAudioTitle;

        public AudioOfflineViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindData(Track track) {
            if (track == null || track.getTitle() == null) return;
            mAudioTitle.setText(track.getTitle());
        }

        @Override
        public void onClick(View view) {
            if (mClickListener == null) return;
            mClickListener.setOnClickListener(getAdapterPosition());
        }
    }
}
