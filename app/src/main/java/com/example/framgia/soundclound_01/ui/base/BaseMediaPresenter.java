package com.example.framgia.soundclound_01.ui.base;

import android.support.annotation.NonNull;

public class BaseMediaPresenter implements BaseMediaContract.Presenter {
    private BaseMediaContract.View mBaseMediaView;

    public BaseMediaPresenter(@NonNull BaseMediaContract.View baseView) {
        mBaseMediaView = baseView;
        mBaseMediaView.setBaseMediaPresenter(this);
    }

    @Override
    public void start() {
        mBaseMediaView.start();
    }
}
