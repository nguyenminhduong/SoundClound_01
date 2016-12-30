package com.example.framgia.soundclound_01.data.model;

import com.google.gson.annotations.SerializedName;

public class Track {
    @SerializedName("artwork_url")
    private String mArtworkUrl;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("download_url")
    private String mDownloadUrl;
    @SerializedName("duration")
    private int mDuration;
    @SerializedName("full_duration")
    private int mFullDuration;
    @SerializedName("id")
    private int mId;
    @SerializedName("permalink")
    private String mPermaLink;
    @SerializedName("permalink_url")
    private String mPermaLinkUrl;
    @SerializedName("playback_count")
    private double mPlaybackCount;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("user")
    private User mUser;

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getFullDuration() {
        return mFullDuration;
    }

    public void setFullDuration(int fullDuration) {
        mFullDuration = fullDuration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPermaLink() {
        return mPermaLink;
    }

    public void setPermaLink(String permaLink) {
        mPermaLink = permaLink;
    }

    public String getPermaLinkUrl() {
        return mPermaLinkUrl;
    }

    public void setPermaLinkUrl(String permaLinkUrl) {
        mPermaLinkUrl = permaLinkUrl;
    }

    public double getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(double playbackCount) {
        mPlaybackCount = playbackCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
