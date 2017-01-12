package com.example.framgia.soundclound_01.ui.main.offline;

import android.content.Context;

import com.example.framgia.soundclound_01.BasePresenter;
import com.example.framgia.soundclound_01.BaseView;
import com.example.framgia.soundclound_01.data.model.Track;

import java.util.List;

public interface AudioOfflineContract {
    interface View extends BaseView<Presenter> {
        void showAudio(List<Track> list);
        void showGetAudioError();
    }

    interface Presenter extends BasePresenter {
        void getAudio(Context context);
    }
}
