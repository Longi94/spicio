package com.tlongdev.spicio.presentation.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.presentation.presenter.fragment.FeedPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.FeedAdapter;
import com.tlongdev.spicio.presentation.ui.view.fragment.FeedView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends SpicioFragment implements FeedView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    private FeedPresenter mPresenter;

    private FeedAdapter mAdapter;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, rootView);

        mPresenter = new FeedPresenter(mApplication);
        mPresenter.attachView(this);

        mAdapter = new FeedAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mPresenter.loadFeed();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showLoadingAnimation() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoadingAnimation() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showFeed(List<UserActivity> activities) {
        mAdapter.setDataSet(activities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }
}
