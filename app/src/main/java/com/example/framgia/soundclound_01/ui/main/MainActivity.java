package com.example.framgia.soundclound_01.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.ui.adapter.ViewPagerAdapter;
import com.example.framgia.soundclound_01.ui.audioresult.AudioResultActivity;
import com.example.framgia.soundclound_01.ui.base.BaseMediaActivity;

import butterknife.BindView;

public class MainActivity extends BaseMediaActivity
    implements MainContract.View, SearchView.OnQueryTextListener {
    @BindView(R.id.tab_layout_main)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbarMain;
    @BindView(R.id.view_pager_main)
    ViewPager mViewPager;
    private SearchView mSearchView;
    private MainContract.Presenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPresenter(new MainPresenter(this));
        mMainPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void start() {
        super.start();
        mToolbarMain.setTitle(R.string.app_name);
        setSupportActionBar(mToolbarMain);
        FragmentManager manager = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager, this);
        mViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mMainPresenter = presenter;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        startActivity(AudioResultActivity.getAudioFromQuery(this, query));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
