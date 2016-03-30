package com.tlongdev.spicio.presentation.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.presenter.activity.SearchFriendsPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.SearchFriendsAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.SearchFriendsView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchFriendsActivity extends SpicioActivity implements SearchFriendsView {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.search) EditText mSearch;

    private SearchFriendsPresenter mPresenter;
    private SearchFriendsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);

        mPresenter = new SearchFriendsPresenter(mApplication);
        mPresenter.attachView(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new SearchFriendsAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mPresenter.search(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showResult(List<User> users) {
        mAdapter.setDataSet(users);
        mAdapter.notifyDataSetChanged();
    }
}
