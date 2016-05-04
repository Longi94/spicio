package com.tlongdev.spicio.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.presenter.activity.UserPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.UserHistoryAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends SpicioActivity implements UserView, UserHistoryAdapter.OnItemClickListener {

    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USER_NAME = "user_name";

    @Inject UserPresenter mPresenter;

    @BindView(R.id.app_bar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fail_text) TextView mFailText;
    @BindView(R.id.avatar) ImageView mAvatar;

    @InjectExtra(EXTRA_USER_ID) long mFriendId;
    @InjectExtra(EXTRA_USER_NAME) String mUserName;

    private UserHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        Dart.inject(this);

        mApplication.getActivityComponent().inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(mUserName);

        mPresenter.setFriendId(mFriendId);
        mPresenter.attachView(this);

        mAdapter = new UserHistoryAdapter();
        mAdapter.setOnItemClickListener(this);

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

    @Override
    public void showUserData(User user, List<com.tlongdev.spicio.domain.model.UserActivity> history) {
        setTitle(user.getName());
        mAdapter.setDataSet(history);
        mAdapter.notifyDataSetChanged();

        Glide.with(this)
                .load(user.getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mAvatar);

        mProgressBar.setVisibility(View.GONE);
        mAppBarLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mFailText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(this, "Failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void friendDeleted() {
        Toast.makeText(this, "Removed!", Toast.LENGTH_LONG).show();
        mAdapter.setButtonText("Add friend");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void friendAdded() {
        Toast.makeText(this, "Added!", Toast.LENGTH_LONG).show();
        mAdapter.setButtonText("Remove friend");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSeriesClick() {
        Intent intent = new Intent(this, UserSeriesActivity.class);
        intent.putExtra(UserSeriesActivity.EXTRA_USER_ID, mFriendId);
        intent.putExtra(UserSeriesActivity.EXTRA_USER_NAME, mUserName);
        startActivity(intent);
    }

    @Override
    public void onFriendsClick() {
        Intent intent = new Intent(this, UserFriendsActivity.class);
        intent.putExtra(UserFriendsActivity.EXTRA_USER_ID, mFriendId);
        intent.putExtra(UserFriendsActivity.EXTRA_USER_NAME, mUserName);
        startActivity(intent);
    }

    @Override
    public void onAddRemoveFriendClick() {
        mPresenter.addOrRemoveFriend();
    }
}
