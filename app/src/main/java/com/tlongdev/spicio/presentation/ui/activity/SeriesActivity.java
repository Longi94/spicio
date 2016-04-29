package com.tlongdev.spicio.presentation.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.presentation.presenter.activity.SeriesPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.SeriesPagerAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeriesActivity extends SpicioActivity implements SeriesView {

    public static final String EXTRA_SERIES_ID = "series_id";
    public static final String EXTRA_SERIES_TITLE = "series_title";

    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout mTabLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;

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

        mPresenter = new SeriesPresenter(mApplication, mSeriesId);
        mPresenter.attachView(this);

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
