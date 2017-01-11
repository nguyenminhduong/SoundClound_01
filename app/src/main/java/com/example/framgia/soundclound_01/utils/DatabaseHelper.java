package com.example.framgia.soundclound_01.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.framgia.soundclound_01.data.model.Track;
import com.example.framgia.soundclound_01.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AUDIO";
    private static final String TABLE_TRACK = "LIST_AUDIO";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TRACK_TITLE = "TITLE";
    private static final String COLUMN_TRACK_ARTWORK_URL = "ARTWORK_URL";
    private static final String COLUMN_TRACK_DOWNLOADABLE = "DOWLOADABLE";
    private static final String COLUMN_TRACK_DURATION = "DURATION";
    private static final String COLUMN_TRACK_URI = "URI";
    private static final String COLUMN_TRACK_PLAYBACK_COUNT = "PLAYBACK_COUNT";
    private static final String COLUMN_TRACK_USER = "USER";
    private static final int COLUMN_ID_NUMBER = 0;
    private static final int COLUMN_TRACK_TITLE_NUMBER = 1;
    private static final int COLUMN_TRACK_ARTWORK_URL_NUMBER = 2;
    private static final int COLUMN_TRACK_DOWNLOADABLE_NUMBER = 3;
    private static final int COLUMN_TRACK_DURATION_NUMBER = 4;
    private static final int COLUMN_TRACK_URI_NUMBER = 5;
    private static final int COLUMN_TRACK_PLAYBACK_COUNT_NUMBER = 6;
    private static final int COLUMN_TRACK_USER_NUMBER = 7;
    private static final int TRUE = 1;
    private static final int FALSE = 0;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TRACK + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_TRACK_TITLE + " TEXT,"
            + COLUMN_TRACK_ARTWORK_URL + " TEXT,"
            + COLUMN_TRACK_DOWNLOADABLE + " INTEGER,"
            + COLUMN_TRACK_DURATION + " INTEGER,"
            + COLUMN_TRACK_URI + " TEXT,"
            + COLUMN_TRACK_PLAYBACK_COUNT + " REAL,"
            + COLUMN_TRACK_USER + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACK);
        onCreate(db);
    }

    public void addListAudio(List<Track> list) {
        if (list == null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for (Track track : list) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_TRACK_TITLE, track.getTitle());
                values.put(COLUMN_TRACK_ARTWORK_URL, track.getArtworkUrl());
                values.put(COLUMN_TRACK_DOWNLOADABLE, track.isDownloadable() ? TRUE : FALSE);
                values.put(COLUMN_TRACK_DURATION, track.getFullDuration());
                values.put(COLUMN_TRACK_URI, track.getUri());
                values.put(COLUMN_TRACK_PLAYBACK_COUNT, track.getPlaybackCount());
                values.put(COLUMN_TRACK_USER, track.getUser().getUserName());
                db.insert(TABLE_TRACK, null, values);
            }
        } catch (Exception ex) {
        } finally {
            db.close();
        }
    }

    public List<Track> getListAudio() {
        List<Track> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRACK;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Track track = new Track();
                    track.setTitle(cursor.getString(COLUMN_TRACK_TITLE_NUMBER));
                    track.setArtworkUrl(cursor.getString(COLUMN_TRACK_ARTWORK_URL_NUMBER));
                    track
                        .setDownloadable((cursor.getInt(COLUMN_TRACK_DOWNLOADABLE_NUMBER) == TRUE));
                    track.setFullDuration(cursor.getInt(COLUMN_TRACK_DURATION_NUMBER));
                    track.setUri(cursor.getString(COLUMN_TRACK_URI_NUMBER));
                    track.setPlaybackCount(cursor.getDouble(COLUMN_TRACK_PLAYBACK_COUNT_NUMBER));
                    User user = new User();
                    user.setUserName(cursor.getString(COLUMN_TRACK_USER_NUMBER));
                    track.setUser(user);
                    list.add(track);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
        } finally {
            db.close();
        }
        return list;
    }

    public void clearListAudio() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRACK, null, null);
        db.close();
    }
}
