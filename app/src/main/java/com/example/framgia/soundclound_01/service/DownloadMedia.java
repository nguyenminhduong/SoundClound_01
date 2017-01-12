package com.example.framgia.soundclound_01.service;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class DownloadMedia {
    private final String mNotificationMess = "Downloading";
    private final String mAudioType = ".mp3";
    private Context mContext;
    private String mFileName;
    private String mUrl;

    public DownloadMedia(Context context, String fileName, String url) {
        mContext = context;
        mFileName = fileName;
        mUrl = url;
    }

    public void startDownload() {
        DownloadManager downloadmanager = (DownloadManager) mContext.getSystemService(Context
            .DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(mUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(mFileName);
        request.setDescription(mNotificationMess);
        request
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                mFileName + mAudioType);
        downloadmanager.enqueue(request);
    }
}
