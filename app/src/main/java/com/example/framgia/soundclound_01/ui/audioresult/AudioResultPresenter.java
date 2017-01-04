package com.example.framgia.soundclound_01.ui.audioresult;

import android.support.annotation.NonNull;

import com.example.framgia.soundclound_01.BuildConfig;
import com.example.framgia.soundclound_01.data.model.AudioResult;
import com.example.framgia.soundclound_01.data.model.CollectionTrack;
import com.example.framgia.soundclound_01.data.model.SearchAudioResult;
import com.example.framgia.soundclound_01.data.model.Track;
import com.example.framgia.soundclound_01.service.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_CLIENT_ID;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_GENRE_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_KIND_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_LIMIT;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_OFFSET;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.PARAM_QUERY;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.VALUE_KIND_TOP;
import static com.example.framgia.soundclound_01.utils.Const.APIConst.VALUE_LIMIT;

public class AudioResultPresenter implements AudioResultContract.Presenter {
    private AudioResultContract.View mAudioResultView;

    public AudioResultPresenter(@NonNull AudioResultContract.View audioView) {
        mAudioResultView = audioView;
        mAudioResultView.setPresenter(this);
    }

    @Override
    public void start() {
        mAudioResultView.start();
    }

    @Override
    public void getAudio(String category, String query, boolean canLoadMore, int offSet) {
        if (!canLoadMore) return;
        if (category == null) getAudioFromSearch(query, offSet);
        else getAudioFromCategory(category, offSet);
    }

    @Override
    public void getAudioFromCategory(String category, int offSet) {
        mAudioResultView.showProgress(true);
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_CLIENT_ID, BuildConfig.API_KEY);
        params.put(PARAM_KIND_AUDIO, VALUE_KIND_TOP);
        params.put(PARAM_GENRE_AUDIO, category);
        params.put(PARAM_LIMIT, VALUE_LIMIT);
        params.put(PARAM_OFFSET, String.valueOf(offSet));
        API.getAudio(params, new Callback<AudioResult>() {
            @Override
            public void onResponse(Call<AudioResult> call, Response<AudioResult> response) {
                mAudioResultView.showProgress(false);
                if (response == null) {
                    mAudioResultView.showGetAudioError();
                    return;
                }
                List<Track> list = new ArrayList<>();
                for (CollectionTrack collectionTrack : response.body().getTracks()) {
                    list.add(collectionTrack.getTrack());
                }
                mAudioResultView.showAudio(list, response.body().getNextHref());
            }

            @Override
            public void onFailure(Call<AudioResult> call, Throwable t) {
                mAudioResultView.showProgress(false);
                mAudioResultView.showGetAudioError();
            }
        });
    }

    @Override
    public void getAudioFromSearch(String query, int offSet) {
        mAudioResultView.showProgress(true);
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_CLIENT_ID, BuildConfig.API_KEY);
        params.put(PARAM_LIMIT, VALUE_LIMIT);
        params.put(PARAM_QUERY, query);
        params.put(PARAM_OFFSET, String.valueOf(offSet));
        API.getAudioResult(params, new Callback<SearchAudioResult>() {
            @Override
            public void onResponse(Call<SearchAudioResult> call,
                                   Response<SearchAudioResult> response) {
                mAudioResultView.showProgress(false);
                if (response == null) {
                    mAudioResultView.showGetAudioError();
                    return;
                }
                mAudioResultView
                    .showAudio(response.body().getTracks(), response.body().getNextHref());
            }

            @Override
            public void onFailure(Call<SearchAudioResult> call, Throwable t) {
                mAudioResultView.showProgress(false);
                mAudioResultView.showGetAudioError();
            }
        });
    }
}
