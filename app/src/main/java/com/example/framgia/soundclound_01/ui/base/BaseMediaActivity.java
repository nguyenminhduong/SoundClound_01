package com.example.framgia.soundclound_01.ui.base;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_UPDATE_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.BROADCAST_UPDATE_CONTROL;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_ICON_PLAY_PAUSE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_IMAGE_URL;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_TITLE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_USER_NAME;

public abstract class BaseMediaActivity extends AppCompatActivity implements BaseMediaContract.View,
    View.OnClickListener {
    @Nullable
    @BindView(R.id.album_art)
    ImageView mImageArt;
    @Nullable
    @BindView(R.id.text_title)
    TextView mTitle;
    @Nullable
    @BindView(R.id.text_artist)
    TextView mArtist;
    @Nullable
    @BindView(R.id.play_previous)
    ImageButton mPlayPre;
    @Nullable
    @BindView(R.id.play_pause)
    ImageButton mPlayPause;
    @Nullable
    @BindView(R.id.play_next)
    ImageButton mPlayNext;
    @Nullable
    @BindView(R.id.controls_container)
    RelativeLayout mRelativeLayout;
    private String mAudioTitle;
    private String mUserName;
    private String mImageUrl;
    private boolean mIsPlay = false;
    private BaseMediaContract.Presenter mBaseMediaPresenter;
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
                case ACTION_STOP:
                    showControl(false);
                    break;
                case ACTION_UPDATE_AUDIO:
                    loadAudioInfor(intent);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setBaseMediaPresenter(new BaseMediaPresenter(this));
    }

    @Override
    public void start() {
        showControl(false);
        setPlayPauseIcon(mIsPlay);
        registerBroadcast();
        getAudioState();
        mPlayPre.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
        mPlayNext.setOnClickListener(this);
    }

    @Override
    public void setBaseMediaPresenter(BaseMediaContract.Presenter presenter) {
        mBaseMediaPresenter = presenter;
    }

    @Override
    public void showControl(boolean show) {
        mRelativeLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void loadAudioInfor(Intent intent) {
        mAudioTitle = intent.getExtras().getString(EXTRA_TITLE);
        mUserName = intent.getExtras().getString(EXTRA_USER_NAME);
        mImageUrl = intent.getExtras().getString(EXTRA_IMAGE_URL);
        setPlayPauseIcon(intent.getExtras().getBoolean(EXTRA_ICON_PLAY_PAUSE));
        if (mAudioTitle != null) mTitle.setText(mAudioTitle);
        if (mUserName != null) mArtist.setText(mUserName);
        if (mImageUrl != null)
            Picasso.with(getApplicationContext()).load(mImageUrl).into(mImageArt);
        else mImageArt.setImageResource(R.drawable.ic_audio_default);
        showControl(true);
    }

    @Override
    public void setPlayPauseIcon(boolean play) {
        mPlayPause.setImageResource(
            play ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
        mIsPlay = !mIsPlay;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_previous:
                startMediaService(ACTION_PREVIOUS);
                break;
            case R.id.play_pause:
                startMediaService(mIsPlay ? ACTION_PAUSE : ACTION_PLAY);
                break;
            case R.id.play_next:
                startMediaService(ACTION_NEXT);
                break;
        }
    }

    private void startMediaService(String action) {
        Intent intent = new Intent(getApplicationContext(), MediaPlayerService.class);
        intent.setAction(action);
        getApplicationContext().startService(intent);
    }

    protected boolean isMediaServiceRunning(Class<MediaPlayerService> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
            .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void registerBroadcast() {
        IntentFilter filterUpdate = new IntentFilter(BROADCAST_UPDATE_CONTROL);
        filterUpdate.addAction(ACTION_UPDATE_AUDIO);
        filterUpdate.addAction(ACTION_PAUSE);
        filterUpdate.addAction(ACTION_PLAY);
        filterUpdate.addAction(ACTION_STOP);
        registerReceiver(mUpdateControl, filterUpdate);
    }

    private void getAudioState() {
        if (!isMediaServiceRunning(MediaPlayerService.class)) return;
        Intent intent = new Intent(getApplicationContext(), MediaPlayerService.class);
        intent.setAction(ACTION_GET_AUDIO_STATE);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUpdateControl);
    }
}
