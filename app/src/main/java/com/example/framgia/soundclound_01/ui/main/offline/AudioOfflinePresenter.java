package com.example.framgia.soundclound_01.ui.main.offline;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.example.framgia.soundclound_01.data.model.Track;

import java.util.ArrayList;
import java.util.List;

public class AudioOfflinePresenter implements AudioOfflineContract.Presenter {
    private static final String IS_MUSIC = "!= 0";
    private static final String SORT_MUSIC = " ASC";
    private AudioOfflineContract.View mAudioOfflineView;

    public AudioOfflinePresenter(@NonNull AudioOfflineContract.View audioOfflineView) {
        mAudioOfflineView = audioOfflineView;
        mAudioOfflineView.setPresenter(this);
    }

    @Override
    public void start() {
        mAudioOfflineView.start();
    }

    @Override
    public void getAudio(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + IS_MUSIC;
        String sortOrder = MediaStore.Audio.Media.TITLE + SORT_MUSIC;
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);
        if (cursor == null && cursor.getCount() <= 0) {
            mAudioOfflineView.showGetAudioError();
            return;
        }
        List<Track> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Track track = new Track();
            track.setTitle(
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            track.setUri(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            track.setFullDuration(
                cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            list.add(track);
        }
        mAudioOfflineView.showAudio(list);
        cursor.close();
    }
}
