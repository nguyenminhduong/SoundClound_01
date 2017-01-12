package com.example.framgia.soundclound_01.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSessionManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.framgia.soundclound_01.BuildConfig;
import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.data.model.PlaybackStatus;
import com.example.framgia.soundclound_01.data.model.Track;
import com.example.framgia.soundclound_01.utils.Const;
import com.example.framgia.soundclound_01.utils.DatabaseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.media.MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_GET_AUDIO_STATE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_NEXT;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_PAUSE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_PLAY;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_PLAY_NEW_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_PREVIOUS;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_UPDATE_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.BROADCAST_UPDATE_CONTROL;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_ICON_PLAY_PAUSE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_IMAGE_URL;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_TITLE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.EXTRA_USER_NAME;
import static com.example.framgia.soundclound_01.utils.StorePreferences.loadAudioIndex;
import static com.example.framgia.soundclound_01.utils.StorePreferences.storeAudioIndex;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener,
    AudioManager.OnAudioFocusChangeListener {
    public static final String ACTION_STOP = "com.soundcloud_01.action.ACTION_STOP";
    public static final String AUDIO_PLAYER = "AUDIO_PLAYER";
    private static final String STREAM_URI = "/stream?" + Const.APIConst
        .PARAM_CLIENT_ID + "=" + BuildConfig.API_KEY;
    private static final String MEDIA_ERROR_NOT_VALID = "MEDIA ERROR NOT VALID FOR PROGRESSIVE " +
        "PLAYBACK";
    private static final String MEDIA_ERROR_SERVER_DIED = "MEDIA ERROR SERVER DIED";
    private static final String MEDIA_ERROR_UNKNOW = "MEDIA ERROR UNKNOWN";
    private static final int ACTION_PLAY_NUMBER = 0;
    private static final int ACTION_PAUSE_NUMBER = 1;
    private static final int ACTION_NEXT_NUMBER = 2;
    private static final int ACTION_PREVIOUS_NUMBER = 3;
    private static final int ACTION_STOP_NUMBER = 4;
    private static final int NOTIFICATION_ID = 101;
    private MediaPlayer mMediaPlayer;
    private MediaSessionManager mMediaSessionManager;
    private MediaSessionCompat mMediaSession;
    private MediaControllerCompat.TransportControls mTransportControls;
    private int mResumePosition;
    private AudioManager mAudioManager;
    private List<Track> mListTrack;
    private int mAudioIndex = -1;
    private Track mTrack;
    private DatabaseHelper mDatabaseHelper;
    private Bitmap mIconBitmap;

    public void loadAudio() {
        mListTrack = mDatabaseHelper.getListAudio();
        mAudioIndex = loadAudioIndex(getApplicationContext());
        if (mAudioIndex == -1 || mAudioIndex > mListTrack.size())
            mAudioIndex = 0;
        mTrack = mListTrack.get(mAudioIndex);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mDatabaseHelper = new DatabaseHelper(getApplicationContext());
            loadAudio();
        } catch (NullPointerException e) {
            stopSelf();
        }
        if (!requestAudioFocus()) {
            stopSelf();
        }
        if (mMediaSessionManager == null) {
            try {
                initMediaSession();
                initMediaPlayer();
            } catch (RemoteException e) {
                e.printStackTrace();
                stopSelf();
            }
            buildNotification(PlaybackStatus.PLAYING);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIncomingActions(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaSession.release();
        removeNotification();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            stopMedia();
            mMediaPlayer.release();
        }
        removeAudioFocus();
        removeNotification();
        mDatabaseHelper.clearListAudio();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        skipToNext();
        updateMetaData();
        buildNotification(PlaybackStatus.PLAYING);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(getApplicationContext(),
                    MEDIA_ERROR_NOT_VALID, Toast.LENGTH_LONG).show();
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(getApplicationContext(),
                    MEDIA_ERROR_SERVER_DIED, Toast.LENGTH_LONG).show();
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(getApplicationContext(),
                    MEDIA_ERROR_UNKNOW, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
        sendBroadCastUpdate();
    }

    @Override
    public void onAudioFocusChange(int focusState) {
        switch (focusState) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (mMediaPlayer == null) initMediaPlayer();
                else if (!mMediaPlayer.isPlaying()) mMediaPlayer.start();
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (mMediaPlayer.isPlaying()) mMediaPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mMediaPlayer.isPlaying()) mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    private boolean requestAudioFocus() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = mAudioManager
            .requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) return true;
        return false;
    }

    private boolean removeAudioFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
            mAudioManager.abandonAudioFocus(this);
    }

    public void initMediaPlayer() {
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        mIconBitmap = null;
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(mTrack.getUri());
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mMediaPlayer.prepareAsync();
    }

    public void playMedia() {
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
        updateMetaData();
    }

    public void stopMedia() {
        if (mMediaPlayer == null || !mMediaPlayer.isPlaying()) return;
        mMediaPlayer.stop();
    }

    public void pauseMedia() {
        if (!mMediaPlayer.isPlaying()) return;
        mMediaPlayer.pause();
        mResumePosition = mMediaPlayer.getCurrentPosition();
    }

    public void resumeMedia() {
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.seekTo(mResumePosition);
        mMediaPlayer.start();
    }

    public void skipToNext() {
        if (mAudioIndex == mListTrack.size() - 1) mAudioIndex = -1;
        mTrack = mListTrack.get(++mAudioIndex);
        storeAudioIndex(getApplicationContext(), mAudioIndex);
        stopMedia();
        mMediaPlayer.reset();
        sendBroadCastUpdate();
        initMediaPlayer();
    }

    public void skipToPrevious() {
        if (mAudioIndex == 0) mAudioIndex = mListTrack.size();
        mTrack = mListTrack.get(--mAudioIndex);
        storeAudioIndex(getApplicationContext(), mAudioIndex);
        stopMedia();
        mMediaPlayer.reset();
        sendBroadCastUpdate();
        initMediaPlayer();
    }

    public void playNewAudio() {
        loadAudio();
        if (mMediaPlayer.isPlaying()) {
            stopMedia();
            mMediaPlayer.reset();
        }
        sendBroadCastUpdate();
        initMediaPlayer();
        updateMetaData();
        buildNotification(PlaybackStatus.PLAYING);
    }

    public void seekTo(int position) {
        if (!mMediaPlayer.isPlaying()) return;
        mMediaPlayer.seekTo(position);
    }

    private void initMediaSession() throws RemoteException {
        if (mMediaSessionManager != null) return;
        mMediaSessionManager =
            (MediaSessionManager) getSystemService(Context.MEDIA_SESSION_SERVICE);
        mMediaSession = new MediaSessionCompat(getApplicationContext(), AUDIO_PLAYER);
        mTransportControls = mMediaSession.getController().getTransportControls();
        mMediaSession.setActive(true);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        updateMetaData();
        mMediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                resumeMedia();
                buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onPause() {
                super.onPause();
                pauseMedia();
                buildNotification(PlaybackStatus.PAUSED);
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                skipToNext();
                updateMetaData();
                buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                skipToPrevious();
                updateMetaData();
                buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onStop() {
                super.onStop();
                stopForeground(true);
                stopMedia();
                stopSelf();
                removeNotification();
            }

            @Override
            public void onSeekTo(long position) {
                super.onSeekTo(position);
            }
        });
    }

    public void updateMetaData() {
        if (mIconBitmap == null) {
            mIconBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_audio_default);
            new GetBitmapImage().execute(mTrack.getArtworkUrl());
        }
        mMediaSession.setMetadata(new MediaMetadataCompat.Builder()
            .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, mIconBitmap)
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, mTrack.getUser().getUserName())
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, mTrack.getTitle())
            .build());
    }

    public void buildNotification(PlaybackStatus playbackStatus) {
        int notificationAction = android.R.drawable.ic_media_pause;
        PendingIntent playPauseAction = null;
        if (playbackStatus == PlaybackStatus.PLAYING) {
            notificationAction = android.R.drawable.ic_media_pause;
            playPauseAction = playbackAction(ACTION_PAUSE_NUMBER);
        } else if (playbackStatus == PlaybackStatus.PAUSED) {
            notificationAction = android.R.drawable.ic_media_play;
            playPauseAction = playbackAction(ACTION_PLAY_NUMBER);
        }
        if (mIconBitmap == null) {
            mIconBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_audio_default);
            new GetBitmapImage().execute(mTrack.getArtworkUrl());
        }
        NotificationCompat.Builder notificationBuilder =
            (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setShowWhen(false)
                .setStyle(new NotificationCompat.MediaStyle()
                    .setMediaSession(mMediaSession.getSessionToken())
                    .setShowActionsInCompactView(ACTION_PLAY_NUMBER, ACTION_PAUSE_NUMBER,
                        ACTION_NEXT_NUMBER))
                .setColor(getResources().getColor(R.color.color_toolbar))
                .setLargeIcon(mIconBitmap)
                .setSmallIcon(android.R.drawable.stat_sys_headset)
                .setContentText(mTrack.getUser().getUserName())
                .setContentTitle(mTrack.getTitle())
                .setContentInfo(mTrack.getTitle())
                .addAction(android.R.drawable.ic_media_previous, ACTION_PREVIOUS,
                    playbackAction(ACTION_PREVIOUS_NUMBER))
                .addAction(notificationAction, ACTION_PAUSE, playPauseAction)
                .addAction(android.R.drawable.ic_media_next, ACTION_NEXT,
                    playbackAction(ACTION_NEXT_NUMBER))
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, ACTION_STOP,
                    playbackAction(ACTION_STOP_NUMBER));
        startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    private PendingIntent playbackAction(int actionNumber) {
        Intent playbackAction = new Intent(this, MediaPlayerService.class);
        switch (actionNumber) {
            case ACTION_PLAY_NUMBER:
                playbackAction.setAction(ACTION_PLAY);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case ACTION_PAUSE_NUMBER:
                playbackAction.setAction(ACTION_PAUSE);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case ACTION_NEXT_NUMBER:
                playbackAction.setAction(ACTION_NEXT);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case ACTION_PREVIOUS_NUMBER:
                playbackAction.setAction(ACTION_PREVIOUS);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case ACTION_STOP_NUMBER:
                playbackAction.setAction(ACTION_STOP);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            default:
                break;
        }
        return null;
    }

    public void removeNotification() {
        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void handleIncomingActions(Intent playbackAction) {
        if (playbackAction == null || playbackAction.getAction() == null) return;
        String actionString = playbackAction.getAction();
        Intent broadcastIntent = new Intent(BROADCAST_UPDATE_CONTROL);
        broadcastIntent.setAction(actionString);
        sendBroadcast(broadcastIntent);
        switch (actionString) {
            case ACTION_PLAY:
                mTransportControls.play();
                break;
            case ACTION_PAUSE:
                mTransportControls.pause();
                break;
            case ACTION_NEXT:
                mTransportControls.skipToNext();
                break;
            case ACTION_PREVIOUS:
                mTransportControls.skipToPrevious();
                break;
            case ACTION_STOP:
                mTransportControls.stop();
                break;
            case ACTION_PLAY_NEW_AUDIO:
                playNewAudio();
                break;
            case ACTION_GET_AUDIO_STATE:
                sendBroadCastUpdate();
                break;
            default:
                break;
        }
    }

    private void sendBroadCastUpdate() {
        Intent broadcastIntent = new Intent(BROADCAST_UPDATE_CONTROL);
        broadcastIntent.setAction(ACTION_UPDATE_AUDIO);
        broadcastIntent.putExtra(EXTRA_TITLE, mTrack.getTitle());
        broadcastIntent.putExtra(EXTRA_USER_NAME, mTrack.getUser().getUserName());
        broadcastIntent.putExtra(EXTRA_IMAGE_URL, mTrack.getArtworkUrl());
        broadcastIntent.putExtra(EXTRA_ICON_PLAY_PAUSE, mMediaPlayer.isPlaying());
        getApplicationContext().sendBroadcast(broadcastIntent);
    }

    private Bitmap getBitmapFromURL(String strUrl) {
        if (strUrl == null) return BitmapFactory.decodeResource(getResources(),
            R.drawable.ic_audio_default);
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class GetBitmapImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            return getBitmapFromURL(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap == null) return;
            mIconBitmap = bitmap;
            updateMetaData();
            buildNotification(PlaybackStatus.PLAYING);
        }
    }
}
