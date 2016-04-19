package com.tlongdev.spicio.presentation.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.fragment.SearchSeriesPresenter;
import com.tlongdev.spicio.presentation.ui.activity.SeriesSearchDetailsActivity;
import com.tlongdev.spicio.presentation.ui.adapter.SearchSeriesAdapter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SearchSeriesView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Outer Layer, UI.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesFragment extends SpicioFragment implements SearchSeriesView, SearchSeriesAdapter.OnItemSelectedListener {

    private static final String LOG_TAG = SearchSeriesFragment.class.getSimpleName();

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.search) EditText mSearchText;

    private SearchSeriesPresenter mPresenter;

    private SearchSeriesAdapter mAdapter;

    public SearchSeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchSeriesPresenter(mApplication);
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_series, container, false);
        ButterKnife.bind(this, rootView);

        //Set the toolbar to the main activity's action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) rootView.findViewById(R.id.toolbar));

        mAdapter = new SearchSeriesAdapter(getActivity());
        mAdapter.setListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mPresenter.searchForSeries(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showSearchResult(List<Series> series) {
        Log.d(LOG_TAG, "showing search result");
        mAdapter.setDataSet(series);
    }

    @Override
    public void showErrorMessage() {
        Log.d(LOG_TAG, "showErrorMessage: "); // TODO: 2016. 03. 06.
        mAdapter.setDataSet(null);
    }

    @Override
    public void onItemSelected(Series series) {
        Log.d(LOG_TAG, "selected item with id: " + series.getTraktId());
        Intent intent = new Intent(getActivity(), SeriesSearchDetailsActivity.class);
        intent.putExtra(SeriesSearchDetailsActivity.EXTRA_TRAKT_ID, series.getTraktId());
        intent.putExtra(SeriesSearchDetailsActivity.EXTRA_POSTER, series.getImages().getPoster().getFull());
        startActivity(intent);
    }
}
