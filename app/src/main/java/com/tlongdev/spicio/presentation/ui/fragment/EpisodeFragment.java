package com.tlongdev.spicio.presentation.ui.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.fragment.EpisodePresenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.EpisodeView;

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

    private static final String ARG_EPISODE_ID = "series_id";

    @Bind(R.id.screenshot) ImageView mScreenShot;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.aired) TextView mAired;
    @Bind(R.id.rating) TextView mRating;
    @Bind(R.id.overview) TextView mOverview;
    @Bind(R.id.check) ImageView mCheck;
    @Bind(R.id.like) ImageView mLike;
    @Bind(R.id.skip) ImageView mSkip;

    private ProgressDialog mLoadingDialog;

    private int mEpisodeId;

    private EpisodePresenter mPresenter;

    public EpisodeFragment() {
        // Required empty public constructor
    }

    public static EpisodeFragment newInstance(int episodeId) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EPISODE_ID, episodeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEpisodeId = getArguments().getInt(ARG_EPISODE_ID);
        }
        mPresenter = new EpisodePresenter((SpicioApplication) getActivity().getApplication());
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
        mPresenter.loadEpisode(mEpisodeId);
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

        updateCheckButton(episode.isWatched());
        updateLikeButton(episode.isLiked());
        updateSkipButton(episode.isSkipped());

        Glide.with(this)
                .load(episode.getImages().getScreenshot().getThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mScreenShot);
    }

    @Override
    public void showError() {

    }

    @OnClick(R.id.check)
    public void checkEpisode() {
        mPresenter.checkEpisode();
    }

    @OnClick(R.id.like)
    public void likeEpisode() {
        mPresenter.likeEpisode();
    }

    @OnClick(R.id.skip)
    public void skipEpisode() {
        mPresenter.skipEpisode();
    }

    @Override
    public void updateLikeButton(boolean liked) {
        mLike.setImageResource(liked ? R.drawable.ic_heart_red : R.drawable.ic_heart_gray);
    }

    @Override
    public void updateCheckButton(boolean watched) {
        mCheck.setImageResource(watched ? R.drawable.ic_check_green : R.drawable.ic_check_gray);
    }

    @Override
    public void updateSkipButton(boolean skipped) {
        mSkip.setImageResource(skipped ? R.drawable.ic_skip_blue : R.drawable.ic_skip_gray);
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog = ProgressDialog.show(getActivity(), null, "Sending to server...", true, false);
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
