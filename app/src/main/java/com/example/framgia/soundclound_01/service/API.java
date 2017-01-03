package com.example.framgia.soundclound_01.service;

import com.example.framgia.soundclound_01.data.model.AudioResult;
import com.example.framgia.soundclound_01.data.model.SearchAudioResult;

import java.util.Map;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.framgia.soundclound_01.utils.Const.APIConst.BASE_URL_SOUNDCLOUD;

public abstract class API {
    private static final APIServices clientSoundCloud = new Retrofit.Builder()
        .baseUrl(BASE_URL_SOUNDCLOUD)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIServices.class);

    public static void getAudio(Map<String, String> params,
                                Callback<AudioResult> callback) {
        clientSoundCloud.getAudio(params)
            .enqueue(callback);
    }

    public static void getAudioResult(Map<String, String> params,
                                      Callback<SearchAudioResult> callback) {
        clientSoundCloud.getAudioResult(params)
            .enqueue(callback);
    }
}
