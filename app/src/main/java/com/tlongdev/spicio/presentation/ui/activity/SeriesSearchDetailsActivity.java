package com.tlongdev.spicio.presentation.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.activity.SeriesSearchDetailsPresenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesSearchDetailsView;

import java.text.DecimalFormat;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeriesSearchDetailsActivity extends SpicioActivity implements SeriesSearchDetailsView {

    private static final String LOG_TAG = SeriesSearchDetailsActivity.class.getSimpleName();

    public static final String EXTRA_TRAKT_ID = "trakt_id";
    public static final String EXTRA_POSTER = "poster";

    @Bind(R.id.poster) ImageView poster;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.overview) TextView overview;
    @Bind(R.id.trailer) Button trailer;
    @Bind(R.id.genres) TextView genres;
    @Bind(R.id.scroll_view) NestedScrollView scrollView;
    @Bind(R.id.progress_bar) ProgressBar progressBar;

    @InjectExtra(EXTRA_TRAKT_ID) int mTraktId;
    @InjectExtra(EXTRA_POSTER) @Nullable String mPoster;

    private SeriesSearchDetailsPresenter mPresenter;

    private String trailerUrl;

    private Series mSeries;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_search_details);
        ButterKnife.bind(this);
        Dart.inject(this);

        mPresenter = new SeriesSearchDetailsPresenter((SpicioApplication) getApplication());
        mPresenter.attachView(this);

        //Set the color of the status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadDetails(mTraktId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showDetails(final Series series) {
        Log.d(LOG_TAG, "show details of " + series.getTitle());
        mSeries = series;

        Glide.with(this)
                .load(mPoster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_movie)
                .into(poster);

        title.setText(series.getTitle());
        overview.setText(series.getOverview());
        genres.setText(String.format("Genres: %s", Arrays.toString(series.getGenres())));

        trailerUrl = series.getTrailer();
        if (trailerUrl == null) {
            trailer.setEnabled(false);
        }

        rating.setText(String.format("%s/10", new DecimalFormat("#.##").format(series.getTraktRating())));

        scrollView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void reportError() {
        Log.w(LOG_TAG, "reportError: failed to get details of a series");
        Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSeriesSaved() {
        Log.d(LOG_TAG, "onSeriesSaved: close the activity");
        finish();
    }

    @Override
    public void showLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mProgressDialog = ProgressDialog.show(
                this, null, "Loading...", true, false
        );
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @OnClick(R.id.trailer)
    public void openTrailer(Button button) {
        Log.d(LOG_TAG, "clicked on trailer button");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(myIntent);
    }

    @OnClick(R.id.save)
    public void saveSeries(Button button) {
        Log.d(LOG_TAG, "clicked on save button");
        mPresenter.saveSeries(mSeries);
    }
}
