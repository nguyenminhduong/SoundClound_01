package com.example.framgia.soundclound_01.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.framgia.soundclound_01.ui.main.fragment.CategoryFragment;
import com.example.framgia.soundclound_01.ui.main.fragment.LocalAudioFragment;
import com.example.framgia.soundclound_01.ui.main.fragment.PopularAudioFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAB_POPULAR = "Popular";
    private static final String TAB_CATEGORY = "Categories";
    private static final String TAB_LOCAL = "Local";
    private static int TAB_NUMBER=3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PopularAudioFragment popularAudioFragment = new PopularAudioFragment();
                return popularAudioFragment;
            case 1:
                CategoryFragment categoryFragment = new CategoryFragment();
                return categoryFragment;
            case 2:
                LocalAudioFragment localAudioFragment = new LocalAudioFragment();
                return localAudioFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return TAB_POPULAR;
            case 1:
                return TAB_CATEGORY;
            case 2:
                return TAB_LOCAL;
            default:
                return null;
        }
    }
}
