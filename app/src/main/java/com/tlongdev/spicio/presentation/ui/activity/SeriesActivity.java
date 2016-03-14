package com.tlongdev.spicio.presentation.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.facebook.appevents.AppEventsLogger;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.presentation.presenter.activity.SeriesPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.SeriesPagerAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SeriesActivity extends AppCompatActivity implements SeriesView {

    public static final String EXTRA_SERIES_ID = "series_id";
    public static final String EXTRA_SERIES_TITLE = "series_title";

    @Bind(R.id.container) ViewPager mViewPager;
    @Bind(R.id.tabs) TabLayout mTabLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @InjectExtra(EXTRA_SERIES_ID) int mSeriesId;
    @InjectExtra(EXTRA_SERIES_TITLE) String mSeriesTitle;

    private SeriesPresenter mPresenter;

    private ProgressDialog mProgressDialog;
    private SeriesPagerAdapter mSeriesPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        ButterKnife.bind(this);
        Dart.inject(this);

        mPresenter = new SeriesPresenter(mSeriesId);
        mPresenter.attachView(this);

        //Set the color of the status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(mSeriesTitle);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSeriesPagerAdapter = new SeriesPagerAdapter(getSupportFragmentManager(), mSeriesId);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSeriesPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        mPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_series, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                mPresenter.refreshSeries();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void reloadData() {
        mSeriesPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mProgressDialog = ProgressDialog.show(
                this, null, "Refreshing...", true, false
        );
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
