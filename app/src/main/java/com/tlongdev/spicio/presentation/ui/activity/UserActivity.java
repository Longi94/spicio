package com.tlongdev.spicio.presentation.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.presenter.activity.UserPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.UserHistoryAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends SpicioActivity implements UserView {

    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USER_NAME = "user_name";

    @Bind(R.id.data_layout) LinearLayout mDataLayout;
    @Bind(R.id.progress_bar) ProgressBar mProgressBar;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.name) TextView mNameView;
    @Bind(R.id.fail_text) TextView mFailText;

    @InjectExtra(EXTRA_USER_ID) long mUserId;
    @InjectExtra(EXTRA_USER_NAME) String mUserName;

    private UserPresenter mPresenter;

    private UserHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        Dart.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(mUserName + "'s profile");

        mPresenter = new UserPresenter(mApplication, mUserId);
        mPresenter.attachView(this);

        mAdapter = new UserHistoryAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getUserData();
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

    @OnClick(R.id.series)
    public void showSeries() {

    }

    @OnClick(R.id.friends)
    public void showFriends() {

    }

    @OnClick(R.id.add_remove_friend)
    public void addOrRemoveFriend() {

    }

    @Override
    public void showUserData(User user, List<com.tlongdev.spicio.domain.model.UserActivity> history) {
        setTitle(user.getName() + "'s profile");
        mNameView.setText(user.getName());
        mAdapter.setDataSet(history);
        mAdapter.notifyDataSetChanged();

        mProgressBar.setVisibility(View.GONE);
        mDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mFailText.setVisibility(View.VISIBLE);
    }
}
