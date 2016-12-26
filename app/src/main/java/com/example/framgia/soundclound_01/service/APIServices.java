package com.example.framgia.soundclound_01.service;

import com.example.framgia.soundclound_01.data.model.AudioResult;
import com.example.framgia.soundclound_01.data.model.SearchAudioResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import static com.example.framgia.soundclound_01.utils.Const.APIConst.Path.PATH_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.Path.PATH_SEARCH_AUDIO;

public interface APIServices {
    @POST(PATH_AUDIO)
    Call<AudioResult> getAudio(@QueryMap Map<String, String> params);
    @POST(PATH_SEARCH_AUDIO)
    Call<SearchAudioResult> getSearChingAudio(@QueryMap Map<String, String> params);
}

