package com.tlongdev.spicio.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.appevents.AppEventsLogger;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.activity.SeasonEpisodesPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.EpisodePagerAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeasonEpisodesView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SeasonEpisodesActivity extends AppCompatActivity implements SeasonEpisodesView {

    public static final String EXTRA_SERIES_ID = "series_id";
    public static final String EXTRA_SEASON = "season";

    @Bind(R.id.container) ViewPager mViewPager;
    @Bind(R.id.tabs) TabLayout mTabLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    private SeasonEpisodesPresenter presenter;

    private EpisodePagerAdapter mEpisodePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SeasonEpisodesPresenter();
        presenter.attachView(this);

        setContentView(R.layout.activity_season_episodes);
        ButterKnife.bind(this);

        //Set the color of the status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int season = intent.getIntExtra(EXTRA_SEASON, -1);
        int seriesId = intent.getIntExtra(EXTRA_SERIES_ID, -1);
        presenter.setSeriesId(seriesId);
        presenter.setSeason(season);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mEpisodePagerAdapter = new EpisodePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mEpisodePagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        if (season == 0) {
            setTitle("Special Episodes");
        } else {
            setTitle("Season " + season);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadEpisodes();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        mEpisodePagerAdapter.setDataSet(episodes);
        mEpisodePagerAdapter.notifyDataSetChanged();
    }
}
