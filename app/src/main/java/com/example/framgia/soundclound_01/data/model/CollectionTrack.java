package com.example.framgia.soundclound_01.data.model;

import com.google.gson.annotations.SerializedName;

public class CollectionTrack {
    @SerializedName("track")
    private Track mTrack;

    public Track getTrack() {
        return mTrack;
    }

    public void setTrack(Track track) {
        mTrack = track;
    }
}
