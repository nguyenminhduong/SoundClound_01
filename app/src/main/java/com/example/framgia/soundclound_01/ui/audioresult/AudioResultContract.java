package com.example.framgia.soundclound_01.ui.audioresult;

import com.example.framgia.soundclound_01.BasePresenter;
import com.example.framgia.soundclound_01.BaseView;
import com.example.framgia.soundclound_01.data.model.Track;

import java.util.List;

public interface AudioResultContract {
    interface View extends BaseView<Presenter> {
        void showAudio(List<Track> list, String nextHref);
        void showGetAudioError();
        void showProgress(boolean show);
    }

    interface Presenter extends BasePresenter {
        void getAudio(String category, String query, boolean canLoadMore, int offSet);
        void getAudioFromCategory(String categoty, int offSet);
        void getAudioFromSearch(String query, int offSet);
    }
}
