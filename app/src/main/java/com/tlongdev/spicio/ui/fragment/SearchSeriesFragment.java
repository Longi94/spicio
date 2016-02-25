package com.tlongdev.spicio.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.model.Series;
import com.tlongdev.spicio.presenter.SearchSeriesPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesFragment extends Fragment implements SearchSeriesView {

    private SearchSeriesPresenter presenter;

    public SearchSeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchSeriesPresenter();
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_series, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.searchForSeries("thrones");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showSearchResult(List<Series> series) {
        Log.d("asd", "yey");
    }

    @Override
    public void showErrorMessage() {

    }
}
