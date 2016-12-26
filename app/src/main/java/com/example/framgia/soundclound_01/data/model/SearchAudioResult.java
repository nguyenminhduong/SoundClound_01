package com.example.framgia.soundclound_01.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Duong on 12/26/2016.
 */
public class SearchAudioResult {
    @SerializedName("collection")
    private List<Track> mTracks;
    @SerializedName("total_results")
    private int mTotalResults;
    @SerializedName("next_href")
    private String mNextHref;

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }
}
