package com.tlongdev.spicio.presentation.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.fragment.SeriesDetailsPresenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeriesDetailsView;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeriesDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeriesDetailsFragment extends SpicioFragment implements SeriesDetailsView {

    private static final String LOG_TAG = SeriesDetailsFragment.class.getSimpleName();

    private static final String ARG_PARAM_SERIES_ID = "series_id";

    @Inject SeriesDetailsPresenter mPresenter;

    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.rating) TextView rating;
    @BindView(R.id.overview) TextView overview;
    @BindView(R.id.trailer) Button trailer;
    @BindView(R.id.genres) TextView genres;

    private String trailerUrl;

    private int mSeriesId = -1;

    private Unbinder mUnbinder;

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
        mApplication.getFragmentComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_series_search_details, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter.attachView(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadSeasonDetails(mSeriesId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mUnbinder.unbind();
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
        genres.setText(TextUtils.join(", ", series.getGenres()));

        trailerUrl = series.getTrailer();
        if (trailerUrl == null) {
            trailer.setEnabled(false);
        }

        rating.setText(String.format("%s/10", new DecimalFormat("#.##").format(series.getTraktRating())));
    }

    @OnClick(R.id.trailer)
    public void openTrailer(Button button) {
        Log.d(LOG_TAG, "clicked on trailer button");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(myIntent);
    }
}
