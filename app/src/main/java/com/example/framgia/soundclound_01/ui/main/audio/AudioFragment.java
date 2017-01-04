package com.example.framgia.soundclound_01.ui.main.audio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.framgia.soundclound_01.R;
import com.example.framgia.soundclound_01.data.model.AudioResult;
import com.example.framgia.soundclound_01.data.model.CollectionTrack;
import com.example.framgia.soundclound_01.data.model.Track;
import com.example.framgia.soundclound_01.ui.adapter.AudioOnlineAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.framgia.soundclound_01.utils.Const.APIConst.VALUE_LIMIT;

public class AudioFragment extends Fragment
    implements AudioContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_audio)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_load_more)
    ProgressBar mProgressLoadMore;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout mSwifeToRefresh;
    private int mPastVisiblesItems;
    private int mVisibleItemCount;
    private int mTotalItemCount;
    private int mOffSet = 0;
    private boolean mCanLoadMore = true;
    private boolean userScrolled = true;
    private AudioContract.Presenter mAudioPresenter;
    private AudioOnlineAdapter mAudioOnlineAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<Track> mTracks = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio, container, false);
        setPresenter(new AudioPresenter(this));
        ButterKnife.bind(this, view);
        mAudioPresenter.start();
        mAudioPresenter.getAudio(mOffSet, mCanLoadMore);
        return view;
    }

    @Override
    public void setPresenter(AudioContract.Presenter presenter) {
        mAudioPresenter = presenter;
    }

    @Override
    public void start() {
        mSwifeToRefresh.setOnRefreshListener(this);
        mAudioOnlineAdapter = new AudioOnlineAdapter(mTracks, getActivity());
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAudioOnlineAdapter);
        implementScrollListener();
    }

    @Override
    public void showAudio(AudioResult audioResult) {
        if (audioResult == null || audioResult.getTracks() == null) return;
        if (audioResult.getNextHref() == null) mCanLoadMore = false;
        mOffSet += Integer.parseInt(VALUE_LIMIT);
        for (CollectionTrack collectionTrack : audioResult.getTracks()) {
            mTracks.add(collectionTrack.getTrack());
        }
        mAudioOnlineAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGetAudioError() {
        Toast.makeText(getActivity(), R.string.get_audio_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        mProgressLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressLoadMore.setVisibility(View.GONE);
    }

    private void implementScrollListener() {
        mRecyclerView
            .addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView,
                                                 int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        userScrolled = true;
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx,
                                       int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mVisibleItemCount = mLinearLayoutManager.getChildCount();
                    mTotalItemCount = mLinearLayoutManager.getItemCount();
                    mPastVisiblesItems = mLinearLayoutManager
                        .findFirstVisibleItemPosition();
                    if (userScrolled
                        && (mVisibleItemCount + mPastVisiblesItems) == mTotalItemCount) {
                        userScrolled = false;
                        mAudioPresenter.getAudio(mOffSet, mCanLoadMore);
                    }
                }
            });
    }

    @Override
    public void onRefresh() {
        mOffSet = 0;
        mCanLoadMore = true;
        mTracks.clear();
        mSwifeToRefresh.setRefreshing(false);
        mAudioPresenter.getAudio(mOffSet, mCanLoadMore);
    }
}
