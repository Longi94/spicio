package com.tlongdev.spicio.presentation.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.presentation.presenter.activity.UserPresenter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends SpicioActivity implements UserView {

    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USER_NAME = "user_name";

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    @InjectExtra(EXTRA_USER_ID) long mUserId;
    @InjectExtra(EXTRA_USER_NAME) String mUserName;

    private UserPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        Dart.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(mUserName + "'s profile");

        mPresenter = new UserPresenter(mApplication);
        mPresenter.attachView(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
