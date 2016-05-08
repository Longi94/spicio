package com.tlongdev.spicio.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.presenter.UserFriendsPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.UserFriendsAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserFriendsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFriendsActivity extends SpicioActivity implements UserFriendsView, UserFriendsAdapter.OnItemClickListener {

    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USER_NAME = "user_name";

    @Inject UserFriendsPresenter mPresenter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @InjectExtra(EXTRA_USER_ID) long mUserId;
    @InjectExtra(EXTRA_USER_NAME) String mUserName;

    private UserFriendsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friends);
        ButterKnife.bind(this);
        Dart.inject(this);

        mApplication.getActivityComponent().inject(this);

        mPresenter.attachView(this);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(mUserName + "' friends");

        mAdapter = new UserFriendsAdapter(this);
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getFriends(mUserId);
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
    public void showFriends(List<User> friends) {
        mAdapter.setDataSet(friends);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(User user) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(UserActivity.EXTRA_USER_ID, user.getId());
        intent.putExtra(UserActivity.EXTRA_USER_NAME, user.getName());
        startActivity(intent);
    }
}
