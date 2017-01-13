package com.example.framgia.soundclound_01.ui.fullplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.service.MediaPlayerService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.framgia.soundclound_01.service.MediaPlayerService.ACTION_STOP;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_GET_AUDIO_STATE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_NEXT;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_PAUSE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_PLAY;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_PREVIOUS;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_SEEK_TO;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_UPDATE_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_UPDATE_SEEK_BAR;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.BROADCAST_UPDATE_CONTROL;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_DURATION;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_FULL_DURATION;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_ICON_PLAY_PAUSE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_IMAGE_URL;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_TITLE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_USER_NAME;

public class FullPlayerActivity extends AppCompatActivity
    implements FullPlayerContract.View, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    @Nullable
    @BindView(R.id.image_album_art)
    ImageView mImageArt;
    @Nullable
    @BindView(R.id.text_title)
    TextView mTitle;
    @Nullable
    @BindView(R.id.text_artist)
    TextView mArtist;
    @Nullable
    @BindView(R.id.image_play_previous)
    ImageButton mPlayPre;
    @Nullable
    @BindView(R.id.image_play_pause)
    ImageButton mPlayPause;
    @Nullable
    @BindView(R.id.image_play_next)
    ImageButton mPlayNext;
    @BindView(R.id.seek_bar_audio)
    SeekBar mSeekBarAudio;
    @BindView(R.id.text_duration)
    TextView mAudioDuration;
    @BindView(R.id.text_full_duration)
    TextView mAudioFullDuration;
    private FullPlayerContract.Presenter mFullPlayerPresenter;
    private String mAudioTitle;
    private String mUserName;
    private String mImageUrl;
    private int mFullDuration;
    private int mDuration;
    private boolean mIsPlay;
    private BroadcastReceiver mUpdateControl = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_PLAY:
                    setPlayPauseIcon(true);
                    break;
                case ACTION_PAUSE:
                    setPlayPauseIcon(false);
                    break;
                case ACTION_UPDATE_AUDIO:
                    loadAudioInfor(intent);
                    break;
                case ACTION_UPDATE_SEEK_BAR:
                    loadSeekBarInfor(intent);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_player);
        setPresenter(new FullPlayerPresenter(this));
        mFullPlayerPresenter.start();
    }

    @Override
    public void setPresenter(FullPlayerContract.Presenter presenter) {
        mFullPlayerPresenter = presenter;
    }

    @Override
    public void start() {
        ButterKnife.bind(this);
        setPlayPauseIcon(mIsPlay);
        registerBroadcast();
        getAudioState();
        mPlayPre.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
        mPlayNext.setOnClickListener(this);
        mSeekBarAudio.setOnSeekBarChangeListener(this);
    }

    @Override
    public void loadAudioInfor(Intent intent) {
        mAudioTitle = intent.getExtras().getString(EXTRA_TITLE);
        mUserName = intent.getExtras().getString(EXTRA_USER_NAME);
        mImageUrl = intent.getExtras().getString(EXTRA_IMAGE_URL);
        loadSeekBarInfor(intent);
        setPlayPauseIcon(intent.getExtras().getBoolean(EXTRA_ICON_PLAY_PAUSE));
        if (mAudioTitle != null) mTitle.setText(mAudioTitle);
        if (mUserName != null) mArtist.setText(mUserName);
        Picasso.with(getApplicationContext())
            .load(mImageUrl)
            .placeholder(R.drawable.ic_audio_default_large)
            .into(mImageArt);
    }

    @Override
    public void setPlayPauseIcon(boolean play) {
        mPlayPause.setImageResource(
            play ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
        mIsPlay = play;
    }

    @Override
    public void getAudioState() {
        Intent intent = new Intent(getApplicationContext(), MediaPlayerService.class);
        intent.setAction(ACTION_GET_AUDIO_STATE);
        startService(intent);
    }

    @Override
    public void loadSeekBarInfor(Intent intent) {
        mDuration = intent.getExtras().getInt(EXTRA_DURATION);
        mFullDuration = intent.getExtras().getInt(EXTRA_FULL_DURATION);
        mFullPlayerPresenter.updateSeekBar(mDuration, mFullDuration);
    }

    @Override
    public void setSeekBar(int progressPercentage, String duration, String fullDuration) {
        mSeekBarAudio.setProgress(progressPercentage);
        mAudioDuration.setText(duration);
        mAudioFullDuration.setText(fullDuration);
    }

    @Override
    public void startServiceUpdateAudio(int duration) {
        Intent intent = new Intent(getApplicationContext(), MediaPlayerService.class);
        intent.putExtra(EXTRA_DURATION, duration);
        intent.setAction(ACTION_SEEK_TO);
        startService(intent);
    }

    private void registerBroadcast() {
        IntentFilter filterUpdate = new IntentFilter(BROADCAST_UPDATE_CONTROL);
        filterUpdate.addAction(ACTION_UPDATE_AUDIO);
        filterUpdate.addAction(ACTION_PAUSE);
        filterUpdate.addAction(ACTION_PLAY);
        filterUpdate.addAction(ACTION_STOP);
        filterUpdate.addAction(ACTION_UPDATE_SEEK_BAR);
        registerReceiver(mUpdateControl, filterUpdate);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_play_previous:
                startMediaService(ACTION_PREVIOUS);
                break;
            case R.id.image_play_pause:
                startMediaService(mIsPlay ? ACTION_PAUSE : ACTION_PLAY);
                break;
            case R.id.image_play_next:
                startMediaService(ACTION_NEXT);
                break;
            default:
                break;
        }
    }

    private void startMediaService(String action) {
        Intent intent = new Intent(getApplicationContext(), MediaPlayerService.class);
        intent.setAction(action);
        getApplicationContext().startService(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int percentage, boolean fromUser) {
        if (!fromUser) return;
        mFullPlayerPresenter.updateAudio(percentage, mFullDuration);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUpdateControl);
    }
}
