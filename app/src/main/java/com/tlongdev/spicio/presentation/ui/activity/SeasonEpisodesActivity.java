package com.tlongdev.spicio.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.ThreadExecutor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.activity.SeasonEpisodesPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.SeasonEpisodesAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeasonEpisodesView;
import com.tlongdev.spicio.threading.MainThreadImpl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SeasonEpisodesActivity extends AppCompatActivity implements SeasonEpisodesView {

    public static final String EXTRA_SERIES_ID = "series_id";
    public static final String EXTRA_SEASON = "season";

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    private SeasonEpisodesPresenter presenter;
    private int mSeriesId;
    private int mSeason;

    private SeasonEpisodesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SeasonEpisodesPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance()
        );
        presenter.attachView(this);

        setContentView(R.layout.activity_season_episodes);
        ButterKnife.bind(this);

        //Set the color of the status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new SeasonEpisodesAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(presenter);

        Intent intent = getIntent();
        mSeriesId = intent.getIntExtra(EXTRA_SERIES_ID, -1);
        mSeason = intent.getIntExtra(EXTRA_SEASON, -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadEpisodes(mSeriesId, mSeason);
        presenter.getEpisodesDetails(mSeriesId, mSeason);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public SpicioApplication getSpicioApplication() {
        return (SpicioApplication) getApplication();
    }

    @Override
    public void showEpisodes(List<Episode> episodes) {
        mAdapter.setDataSet(episodes);
        mAdapter.notifyDataSetChanged();
    }
}
