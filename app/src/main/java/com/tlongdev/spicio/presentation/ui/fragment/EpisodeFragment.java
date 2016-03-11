package com.tlongdev.spicio.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.ThreadExecutor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.fragment.EpisodePresenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.EpisodeView;
import com.tlongdev.spicio.threading.MainThreadImpl;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpisodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpisodeFragment extends Fragment implements EpisodeView {

    private static final String ARG_SERIES_ID = "series_id";
    private static final String ARG_SEASON = "season";
    private static final String ARG_EPISODE = "episode";

    @Bind(R.id.screenshot) ImageView mScreenShot;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.aired) TextView mAired;
    @Bind(R.id.rating) TextView mRating;
    @Bind(R.id.overview) TextView mOverview;

    private int mSeriesId;
    private int mSeason;
    private int mEpisode;

    private EpisodePresenter mPresenter;

    public EpisodeFragment() {
        // Required empty public constructor
    }

    public static EpisodeFragment newInstance(int seriesId, int season, int episode) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SERIES_ID, seriesId);
        args.putInt(ARG_SEASON, season);
        args.putInt(ARG_EPISODE, episode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSeriesId = getArguments().getInt(ARG_SERIES_ID);
            mSeason = getArguments().getInt(ARG_SEASON);
            mEpisode = getArguments().getInt(ARG_EPISODE);
        }
        mPresenter = new EpisodePresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance()
        );
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_episode, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadEpisode(mSeriesId, mSeason, mEpisode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showEpisodeDetails(Episode episode) {
        mTitle.setText(episode.getTitle());
        mOverview.setText(episode.getOverview());

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        mAired.setText(formatter.print(episode.getFirstAired()));

        mRating.setText(String.format("%s/10", new DecimalFormat("#.##").format(episode.getTraktRating())));

        Glide.with(this)
                .load(episode.getImages().getScreenshot().getThumb())
                .into(mScreenShot);
    }

    @Override
    public void showError() {

    }

    @Override
    public SpicioApplication getSpicioApplication() {
        return (SpicioApplication) getActivity().getApplication();
    }

    @OnClick(R.id.check)
    public void checkEpisode() {

    }

    @OnClick(R.id.like)
    public void likeEpisode() {

    }
}
