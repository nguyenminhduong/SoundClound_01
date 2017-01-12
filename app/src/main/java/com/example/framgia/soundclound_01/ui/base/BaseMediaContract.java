package com.example.framgia.soundclound_01.ui.base;

import android.content.Intent;

public interface BaseMediaContract {
    interface View {
        void setBaseMediaPresenter(Presenter presenter);
        void start();
        void showControl(boolean show);
        void loadAudioInfor(Intent intent);
        void setPlayPauseIcon(boolean play);
    }

    interface Presenter {
        void start();
    }
}
