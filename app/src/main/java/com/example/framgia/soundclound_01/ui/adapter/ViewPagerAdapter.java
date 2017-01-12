package com.example.framgia.soundclound_01.ui.adapter;
/**
 * Created by Duong on 12/29/2016.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.ui.main.audio.AudioFragment;
import com.example.framgia.soundclound_01.ui.main.category.CategoryFragment;
import com.example.framgia.soundclound_01.ui.main.offline.AudioOfflineFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static int TAB_NUMBER = 3;
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AudioFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new AudioOfflineFragment();
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
                return mContext.getString(R.string.popular);
            case 1:
                return mContext.getString(R.string.category);
            case 2:
                return mContext.getString(R.string.local);
            default:
                return null;
        }
    }
}
