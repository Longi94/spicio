package com.tlongdev.spicio.presentation.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.ThreadExecutor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.fragment.SeriesDetailsPresenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeriesDetailsView;
import com.tlongdev.spicio.threading.MainThreadImpl;

import java.text.DecimalFormat;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeriesDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeriesDetailsFragment extends Fragment implements SeriesDetailsView {

    private static final String LOG_TAG = SeriesDetailsFragment.class.getSimpleName();

    private static final String ARG_PARAM_SERIES_ID = "series_id";

    @Bind(R.id.poster) ImageView poster;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.overview) TextView overview;
    @Bind(R.id.trailer) Button trailer;
    @Bind(R.id.genres) TextView genres;

    private SeriesDetailsPresenter presenter;

    private String trailerUrl;

    private int mSeriesId = -1;

    public SeriesDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param seriesId the trakt ID of the series
     * @return A new instance of fragment SeriesDetailsFragment.
     */
    public static SeriesDetailsFragment newInstance(int seriesId) {
        SeriesDetailsFragment fragment = new SeriesDetailsFragment();
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

        presenter = new SeriesDetailsPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance()
        );
        presenter.attachView(this);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_series_search_details, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadSeasonDetails(mSeriesId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showDetails(Series series) {
        Log.d(LOG_TAG, "show details of " + series.getTitle());

        Glide.with(this)
                .load(series.getImages().getPoster().getFull())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_movie)
                .into(poster);

        title.setText(series.getTitle());
        overview.setText(series.getOverview());
        genres.setText(String.format("Genres: %s", Arrays.toString(series.getGenres())));

        trailerUrl = series.getTrailer();
        if (trailerUrl == null) {
            trailer.setEnabled(false);
        }

        rating.setText(String.format("%s/10", new DecimalFormat("#.##").format(series.getTraktRating())));
    }

    @Override
    public SpicioApplication getSpicioApplication() {
        return (SpicioApplication) getActivity().getApplication();
    }

    @OnClick(R.id.trailer)
    public void openTrailer(Button button) {
        Log.d(LOG_TAG, "clicked on trailer button");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(myIntent);
    }
}
