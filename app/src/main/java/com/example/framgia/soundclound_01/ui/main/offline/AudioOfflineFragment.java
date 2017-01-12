package com.example.framgia.soundclound_01.ui.main.offline;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.data.model.Track;
import com.example.framgia.soundclound_01.service.MediaPlayerService;
import com.example.framgia.soundclound_01.ui.adapter.AudioOfflineAdapter;
import com.example.framgia.soundclound_01.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.framgia.soundclound_01.utils.Const.IntentKey.ACTION_PLAY_NEW_AUDIO;
import static com.example.framgia.soundclound_01.utils.Const.RequestCode.PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE;
import static com.example.framgia.soundclound_01.utils.StorePreferences.storeAudioIndex;

public class AudioOfflineFragment extends Fragment
    implements AudioOfflineContract.View, SwipeRefreshLayout.OnRefreshListener,
    AudioOfflineAdapter.ClickListener {
    @BindView(R.id.recycler_view_audio)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwifeToRefresh;
    private AudioOfflineAdapter mAudioOfflineAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private AudioOfflineContract.Presenter mAudioOfflinePresenter;
    private List<Track> mTracks = new ArrayList<>();
    private DatabaseHelper mDatabaseHelper;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio, container, false);
        setPresenter(new AudioOfflinePresenter(this));
        ButterKnife.bind(this, view);
        mAudioOfflinePresenter.start();
        checkPermission();
        return view;
    }

    @Override
    public void start() {
        mSwifeToRefresh.setOnRefreshListener(this);
        mAudioOfflineAdapter = new AudioOfflineAdapter(mTracks, getActivity(), this);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAudioOfflineAdapter);
        mDatabaseHelper = new DatabaseHelper(getActivity());
    }

    @Override
    public void showAudio(List<Track> list) {
        if (list == null) return;
        mTracks.clear();
        mTracks.addAll(list);
        mAudioOfflineAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGetAudioError() {
        Toast.makeText(getActivity(), R.string.get_audio_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(AudioOfflineContract.Presenter presenter) {
        mAudioOfflinePresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mSwifeToRefresh.setRefreshing(false);
        checkPermission();
    }

    @Override
    public void setOnClickListener(int index) {
        playAudio(index);
    }

    private void playAudio(int audioIndex) {
        mDatabaseHelper.clearListAudio();
        mDatabaseHelper.addListAudioOffline(mTracks);
        storeAudioIndex(getActivity(), audioIndex);
        Intent intent = new Intent(getActivity(), MediaPlayerService.class);
        intent.setAction(ACTION_PLAY_NEW_AUDIO);
        getActivity().startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            mAudioOfflinePresenter.getAudio(getActivity());
        else Toast.makeText(getActivity(), R.string.permission_Denied,
            Toast.LENGTH_LONG).show();
    }

    private void checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(),
            WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            mAudioOfflinePresenter.getAudio(getActivity());
        } else {
            requestPermissions(new String[]{
                WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
        }
    }
}
