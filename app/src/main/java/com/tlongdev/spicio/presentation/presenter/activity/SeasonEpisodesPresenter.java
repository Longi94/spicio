package com.tlongdev.spicio.presentation.presenter.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.LoadSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.SaveEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.SaveEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeasonEpisodesView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class SeasonEpisodesPresenter implements Presenter<SeasonEpisodesView>,
        SwipeRefreshLayout.OnRefreshListener, LoadSeasonEpisodesInteractor.Callback,
        TraktSeasonEpisodesInteractor.Callback {

    private SeasonEpisodesView mView;

    private SpicioApplication mApplication;

    private int mSeriesId;
    private int mSeason;

    @Override
    public void attachView(SeasonEpisodesView view) {
        mView = view;
        mApplication = view.getSpicioApplication();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void getEpisodesDetails() {
        mView.showRefreshAnimation();
        TraktSeasonEpisodesInteractor interactor = new TraktSeasonEpisodesInteractorImpl(
                mApplication, mSeriesId, mSeason, this
        );
        interactor.execute();
    }

    public void loadEpisodes() {
        LoadSeasonEpisodesInteractor interactor = new LoadSeasonEpisodesInteractorImpl(
                mApplication, mSeriesId, mSeason, this
        );
        interactor.execute();
    }

    @Override
    public void onRefresh() {
        getEpisodesDetails();
    }

    public void setSeriesId(int seriesId) {
        mSeriesId = seriesId;
    }

    public void setSeason(int season) {
        mSeason = season;
    }

    @Override
    public void onLoadSeasonEpisodesFinish(List<Episode> episodes) {
        if (mView != null) {
            mView.showEpisodes(episodes);
        }
    }

    @Override
    public void onLoadSeasonEpisodesFail() {
        // TODO: 2016. 03. 11.
    }

    @Override
    public void onTraktSeasonEpisodesFinish(List<Episode> episodes) {
        SaveEpisodesInteractor interactor = new SaveEpisodesInteractorImpl(
                mApplication, episodes, null
        );
        interactor.execute();

        if (mView != null) {
            mView.showEpisodes(episodes);
            mView.hideRefreshAnimation();
        }
    }

    @Override
    public void onTraktSeasonEpisodesFail() {
        if (mView != null) {
            mView.hideRefreshAnimation();
        }
    }
}
