package com.tlongdev.spicio.presentation.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.executor.ThreadExecutor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.SeriesDetailsPresenter;
import com.tlongdev.spicio.threading.MainThreadImpl;

import butterknife.ButterKnife;

public class SeriesDetailsActivity extends AppCompatActivity implements SeriesDetailsView {

    public static final String EXTRA_TRAKT_ID = "trakt_id";

    private SeriesDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SeriesDetailsPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance());
        presenter.attachView(this);

        setContentView(R.layout.activity_series_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void showDetails(Series series) {

    }
}
