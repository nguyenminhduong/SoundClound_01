package com.example.framgia.soundclound_01.ui.main.audio;

import com.example.framgia.soundclound_01.BasePresenter;
import com.example.framgia.soundclound_01.BaseView;
import com.example.framgia.soundclound_01.data.model.AudioResult;

public interface AudioContract {
    interface View extends BaseView<Presenter> {
        void showAudio(AudioResult audioResult);
        void showGetAudioError();
        void showProgress();
        void hideProgress();
    }

    interface Presenter extends BasePresenter {
        void getAudio(int offSet, boolean canLoadMore);
    }
}
