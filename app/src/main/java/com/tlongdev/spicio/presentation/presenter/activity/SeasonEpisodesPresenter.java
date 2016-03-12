package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.LoadSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeasonEpisodesView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class SeasonEpisodesPresenter implements Presenter<SeasonEpisodesView>,
        LoadSeasonEpisodesInteractor.Callback{

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

    public void loadEpisodes() {
        LoadSeasonEpisodesInteractor interactor = new LoadSeasonEpisodesInteractorImpl(
                mApplication, mSeriesId, mSeason, this
        );
        interactor.execute();
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
}
