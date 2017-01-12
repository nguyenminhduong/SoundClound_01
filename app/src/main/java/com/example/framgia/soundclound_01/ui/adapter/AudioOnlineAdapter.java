package com.example.framgia.soundclound_01.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.data.model.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.framgia.soundclound_01.utils.Const.APIConst.STREAM_URL;

public class AudioOnlineAdapter
    extends RecyclerView.Adapter<AudioOnlineAdapter.MyViewHolder> {
    private static final String FORMAT_NUMBER = "%1$,.0f";
    private List<Track> mListTracks;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ClickListener mClickListener;

    public AudioOnlineAdapter(List<Track> list, Context context, ClickListener clickListener) {
        mListTracks = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mClickListener = clickListener;
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

    public interface ClickListener {
        void setOnClickListener(int index);
        void setOnDownloadListener(String title, String url);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_audio_title)
        TextView mAudioTitle;
        @BindView(R.id.text_user_name)
        TextView mUserName;
        @BindView(R.id.text_playback_count)
        TextView mPlaybackCount;
        @BindView(R.id.image_audio_icon)
        ImageView mImageAudioIcon;
        @BindView(R.id.button_download)
        ImageButton mButtonDownload;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindData(Track track) {
            if (track == null || track.getUser() == null) return;
            mAudioTitle.setText(track.getTitle());
            mUserName.setText(track.getUser().getUserName());
            mPlaybackCount.setText(String.format(FORMAT_NUMBER, track.getPlaybackCount()));
            Picasso.with(mContext)
                .load(track.getArtworkUrl())
                .into(mImageAudioIcon);
            mButtonDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener == null) return;
                    mClickListener.setOnDownloadListener
                        (mListTracks.get(getAdapterPosition()).getTitle(), mListTracks.get
                            (getAdapterPosition()).getUri() + STREAM_URL);
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener == null) return;
            mClickListener.setOnClickListener(getAdapterPosition());
        }
    }
}
