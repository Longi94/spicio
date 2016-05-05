package com.tlongdev.spicio.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.presentation.presenter.fragment.FeedPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.FeedAdapter;
import com.tlongdev.spicio.presentation.ui.view.fragment.FeedView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends SpicioFragment implements FeedView, SwipeRefreshLayout.OnRefreshListener {

    @Inject FeedPresenter mPresenter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    private FeedAdapter mAdapter;

    private Unbinder mUnbinder;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication.getFragmentComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        mPresenter.attachView(this);

        //Set the toolbar to the main activity's action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) rootView.findViewById(R.id.toolbar));

        mAdapter = new FeedAdapter(getActivity());

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
        mUnbinder.unbind();
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
