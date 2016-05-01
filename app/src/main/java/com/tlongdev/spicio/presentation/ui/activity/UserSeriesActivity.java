package com.tlongdev.spicio.presentation.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.activity.UserSeriesPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.UserSeriesAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserSeriesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSeriesActivity extends SpicioActivity implements UserSeriesView {

    public static final String EXTRA_USER_ID = "user_id";

    @Inject UserSeriesPresenter mPresenter;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @InjectExtra(EXTRA_USER_ID) long mUserId;

    private UserSeriesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_series);
        ButterKnife.bind(this);
        Dart.inject(this);

        mApplication.getActivityComponent().inject(this);

        mPresenter.attachView(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new UserSeriesAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getSeries(mUserId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showSeries(List<Series> series) {
        mAdapter.setDataSet(series);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
    }
}
