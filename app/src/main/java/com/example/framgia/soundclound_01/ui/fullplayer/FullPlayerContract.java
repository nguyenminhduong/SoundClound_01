package com.example.framgia.soundclound_01.ui.fullplayer;

import android.content.Intent;

import com.example.framgia.soundclound_01.BasePresenter;
import com.example.framgia.soundclound_01.BaseView;

public interface FullPlayerContract {
    interface View extends BaseView<Presenter> {
        void loadAudioInfor(Intent intent);
        void setPlayPauseIcon(boolean play);
        void getAudioState();
        void loadSeekBarInfor(Intent intent);
        void setSeekBar(int progressPercentage, String duration, String fullDuration);
        void startServiceUpdateAudio(int duration);
    }

    interface Presenter extends BasePresenter {
        void updateSeekBar(int duration, int fullDuration);
        void updateAudio(int progressPercentage, int fullDuration);
    }
}
