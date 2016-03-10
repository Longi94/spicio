package com.tlongdev.spicio.presentation.presenter.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.LoadSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.TraktEpisodeImagesInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktEpisodeImagesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.AbstractPresenter;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeasonEpisodesView;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class SeasonEpisodesPresenter extends AbstractPresenter implements Presenter<SeasonEpisodesView>,TraktEpisodeImagesInteractor.Callback, SwipeRefreshLayout.OnRefreshListener, LoadSeasonEpisodesInteractor.Callback {

    private SeasonEpisodesView mView;

    public SeasonEpisodesPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }

    @Override
    public void attachView(SeasonEpisodesView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void getEpisodesDetails(int seriesId, int season) {
        TraktEpisodeImagesInteractor interactor = new TraktEpisodeImagesInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), seriesId, season, this
        );
        interactor.execute();
    }

    public void loadEpisodes(int seriesId, int season) {
        LoadSeasonEpisodesInteractor interactor = new LoadSeasonEpisodesInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), seriesId, season, this
        );
        interactor.execute();
    }

    @Override
    public void onFinish(List<Episode> episodes) {
        if (mView != null) {
            mView.showEpisodes(episodes);
        }
    }

    @Override
    public void onFail() {
        // TODO: 2016. 03. 10.
    }

    @Override
    public void onRefresh() {

    }
}
