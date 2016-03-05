package com.tlongdev.spicio.presentation.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.ThreadExecutor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.impl.TraktRepositoryImpl;
import com.tlongdev.spicio.presentation.presenter.SearchSeriesPresenter;
import com.tlongdev.spicio.presentation.ui.activity.SeriesDetailsActivity;
import com.tlongdev.spicio.presentation.ui.adapter.SearchSeriesAdapter;
import com.tlongdev.spicio.threading.MainThreadImpl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Outer Layer, UI.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesFragment extends Fragment implements SearchSeriesView, SearchSeriesAdapter.OnItemSelectedListener {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.search) EditText mSearchText;

    private SearchSeriesPresenter presenter;

    private SearchSeriesAdapter adapter;

    public SearchSeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchSeriesPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                new TraktRepositoryImpl(((SpicioApplication)getActivity().getApplication())
                        .getNetworkComponent())
        );
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_series, container, false);
        ButterKnife.bind(this, rootView);

        //Set the toolbar to the main activity's action bar
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) rootView.findViewById(R.id.toolbar));

        adapter = new SearchSeriesAdapter(getActivity());
        adapter.setListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    presenter.searchForSeries(v.getText().toString());
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
        presenter.detachView();
    }

    @Override
    public void showSearchResult(List<Series> series) {
        adapter.setDataSet(series);
    }

    @Override
    public void showErrorMessage() {
        adapter.setDataSet(null);
    }

    @Override
    public void onItemSelected(Series series) {
        Intent intent = new Intent(getActivity(), SeriesDetailsActivity.class);
        intent.putExtra(SeriesDetailsActivity.EXTRA_TRAKT_ID, series.getTraktId());
        intent.putExtra(SeriesDetailsActivity.EXTRA_POSTER, series.getImages().getPoster().getFull());
        startActivity(intent);
    }
}
