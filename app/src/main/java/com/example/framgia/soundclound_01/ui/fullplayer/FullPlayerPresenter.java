package com.example.framgia.soundclound_01.ui.fullplayer;

import java.util.concurrent.TimeUnit;

public class FullPlayerPresenter implements FullPlayerContract.Presenter {
    private final int PERCENTAGE = 100;
    private FullPlayerContract.View mFullPlayerView;

    public FullPlayerPresenter(FullPlayerContract.View fullPlayerView) {
        mFullPlayerView = fullPlayerView;
    }

    @Override
    public void start() {
        mFullPlayerView.start();
    }

    @Override
    public void updateSeekBar(int duration, int fullDuration) {
        mFullPlayerView.setSeekBar(convertProgressPercentage(duration, fullDuration), convertTime
            (duration), convertTime(fullDuration));
    }

    @Override
    public void updateAudio(int progressPercentage, int fullDuration) {
        mFullPlayerView
            .startServiceUpdateAudio(convertProgressToTimer(progressPercentage, fullDuration));
    }

    private String convertTime(int millis) {
        if (TimeUnit.MILLISECONDS.toHours(millis) > 0)
            return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public int convertProgressPercentage(int currentDuration, int totalDuration) {
        return currentDuration * PERCENTAGE / totalDuration;
    }

    public int convertProgressToTimer(int progress, int fullDuration) {
        return progress * fullDuration / PERCENTAGE;
    }
}
