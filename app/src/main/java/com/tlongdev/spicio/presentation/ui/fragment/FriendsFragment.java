package com.tlongdev.spicio.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.presenter.fragment.FriendsPresenter;
import com.tlongdev.spicio.presentation.ui.activity.SearchFriendsActivity;
import com.tlongdev.spicio.presentation.ui.activity.UserActivity;
import com.tlongdev.spicio.presentation.ui.adapter.FriendsAdapter;
import com.tlongdev.spicio.presentation.ui.view.fragment.FriendsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends SpicioFragment implements FriendsView, FriendsAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private FriendsPresenter mPresenter;

    private FriendsAdapter mAdapter;

    private Unbinder mUnbinder;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        mPresenter = new FriendsPresenter(mApplication);
        mPresenter.attachView(this);

        //Set the toolbar to the main activity's action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) rootView.findViewById(R.id.toolbar));

        mAdapter = new FriendsAdapter();
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadFriends();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
        mUnbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_friends, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_search:
                //Start the search activity
                startActivity(new Intent(getActivity(), SearchFriendsActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void showFriends(List<User> friends) {
        mAdapter.setDataSet(friends);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(User user) {
        Intent intent = new Intent(getActivity(), UserActivity.class);
        intent.putExtra(UserActivity.EXTRA_USER_ID, user.getId());
        intent.putExtra(UserActivity.EXTRA_USER_NAME, user.getName());
        startActivity(intent);
    }
}
