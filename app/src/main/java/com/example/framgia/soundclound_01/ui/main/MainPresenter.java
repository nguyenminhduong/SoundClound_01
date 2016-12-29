package com.example.framgia.soundclound_01.ui.main;

import android.support.annotation.NonNull;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mMainView;

    public MainPresenter(@NonNull MainContract.View mainView) {
        mMainView = mainView;
        mainView.setPresenter(this);
    }

    @Override
    public void start() {
        mMainView.start();
    }
}
