package com.example.framgia.soundclound_01.service;

import com.example.framgia.soundclound_01.data.model.AudioResult;
import com.example.framgia.soundclound_01.data.model.SearchAudioResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.example.framgia.soundclound_01.utils.Const.APIConst.PATH_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PATH_SEARCH_AUDIO;

public interface APIServices {
    @GET(PATH_AUDIO)
    Call<AudioResult> getAudio(@QueryMap Map<String, String> params);
    @GET(PATH_SEARCH_AUDIO)
    Call<SearchAudioResult> getAudioResult(@QueryMap Map<String, String> params);
}

