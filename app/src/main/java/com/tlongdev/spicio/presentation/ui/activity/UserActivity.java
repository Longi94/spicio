package com.tlongdev.spicio.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import butterknife.OnClick;

public class UserActivity extends SpicioActivity implements UserView {

    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USER_NAME = "user_name";

    @Inject UserPresenter mPresenter;

    @BindView(R.id.data_layout) LinearLayout mDataLayout;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.name) TextView mNameView;
    @BindView(R.id.fail_text) TextView mFailText;
    @BindView(R.id.add_remove_friend) Button mAddRemoveButton;

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

        setTitle(mUserName + "'s profile");

        mPresenter.setFriendId(mFriendId);
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
        Intent intent = new Intent(this, UserSeriesActivity.class);
        intent.putExtra(UserSeriesActivity.EXTRA_USER_ID, mFriendId);
        intent.putExtra(UserSeriesActivity.EXTRA_USER_NAME, mUserName);
        startActivity(intent);
    }

    @OnClick(R.id.friends)
    public void showFriends() {
        Intent intent = new Intent(this, UserFriendsActivity.class);
        intent.putExtra(UserFriendsActivity.EXTRA_USER_ID, mFriendId);
        intent.putExtra(UserFriendsActivity.EXTRA_USER_NAME, mUserName);
        startActivity(intent);
    }

    @OnClick(R.id.add_remove_friend)
    public void addOrRemoveFriend() {
        mPresenter.addOrRemoveFriend();
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

    @Override
    public void showErrorToast() {
        Toast.makeText(this, "Failed!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void friendDeleted() {
        Toast.makeText(this, "Removed!", Toast.LENGTH_LONG).show();
        mAddRemoveButton.setText("Add friend");
    }

    @Override
    public void friendAdded() {
        Toast.makeText(this, "Added!", Toast.LENGTH_LONG).show();
        mAddRemoveButton.setText("Remove friend");
    }
}
