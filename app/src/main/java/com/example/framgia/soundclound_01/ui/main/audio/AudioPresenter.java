package com.example.framgia.soundclound_01.ui.main.audio;

import android.support.annotation.NonNull;

import com.example.framgia.soundclound_01.BuildConfig;
import com.example.framgia.soundclound_01.data.model.AudioResult;
import com.example.framgia.soundclound_01.service.API;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_CLIENT_ID;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_GENRE_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_KIND_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_LIMIT;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_OFFSET;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.VALUE_GENRE_POPULAR;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.VALUE_KIND_TOP;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.VALUE_LIMIT;

public class AudioPresenter implements AudioContract.Presenter {
    private AudioContract.View mAudioView;

    public AudioPresenter(@NonNull AudioContract.View audioView) {
        mAudioView = audioView;
        mAudioView.setPresenter(this);
    }

    @Override
    public void start() {
        mAudioView.start();
    }

    @Override
    public void getAudio(int offSet, boolean canLoadMore) {
        if (!canLoadMore) return;
        mAudioView.showProgress();
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_CLIENT_ID, BuildConfig.API_KEY);
        params.put(PARAM_KIND_AUDIO, VALUE_KIND_TOP);
        params.put(PARAM_GENRE_AUDIO, VALUE_GENRE_POPULAR);
        params.put(PARAM_LIMIT, VALUE_LIMIT);
        params.put(PARAM_OFFSET, String.valueOf(offSet));
        API.getAudio(params, new Callback<AudioResult>() {
            @Override
            public void onResponse(Call<AudioResult> call, Response<AudioResult> response) {
                mAudioView.hideProgress();
                if (response == null) {
                    mAudioView.showGetAudioError();
                    return;
                }
                mAudioView.showAudio(response.body());
            }

            @Override
            public void onFailure(Call<AudioResult> call, Throwable t) {
                mAudioView.hideProgress();
                mAudioView.showGetAudioError();
            }
        });
    }
}
