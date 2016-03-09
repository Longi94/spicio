package com.tlongdev.spicio.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.ThreadExecutor;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.presentation.presenter.SeasonsPresenter;
import com.tlongdev.spicio.presentation.ui.adapter.SeasonsAdapter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeasonsView;
import com.tlongdev.spicio.threading.MainThreadImpl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeasonsFragment extends Fragment implements SeasonsView {

    private static final String ARG_PARAM_SERIES_ID = "series_id";

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;

    private SeasonsAdapter adapter;

    private SeasonsPresenter presenter;
    private int mSeriesId = -1;

    public SeasonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param seriesId the trakt ID of the series
     * @return A new instance of fragment SeriesDetailsFragment.
     */
    public static SeasonsFragment newInstance(int seriesId) {
        SeasonsFragment fragment = new SeasonsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_SERIES_ID, seriesId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSeriesId = getArguments().getInt(ARG_PARAM_SERIES_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = new SeasonsPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance()
        );
        presenter.attachView(this);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_seasons, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new SeasonsAdapter(getActivity());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadSeasons(mSeriesId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showSeasons(List<Season> seasons) {
        adapter.setDataSet(seasons);
        adapter.notifyDataSetChanged();
    }

    @Override
    public SpicioApplication getSpicioApplication() {
        return (SpicioApplication) getActivity().getApplication();
    }
}
