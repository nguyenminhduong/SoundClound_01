package com.example.framgia.soundclound_01.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AudioResult {
    @SerializedName("collection")
    private List<CollectionTrack> mTracks;
    @SerializedName("next_href")
    private String mNextHref;

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }

    public List<CollectionTrack> getTracks() {
        return mTracks;
    }

    public void setTracks(
        List<CollectionTrack> collection) {
        mTracks = collection;
    }
}
