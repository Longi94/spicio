package com.tlongdev.spicio.presentation.presenter.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.LoadSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.SaveEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.SaveEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeasonEpisodesInteractorImpl;
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
public class SeasonEpisodesPresenter extends AbstractPresenter implements Presenter<SeasonEpisodesView>,
        SwipeRefreshLayout.OnRefreshListener {

    private SeasonEpisodesView mView;

    private SpicioApplication mApplication;

    private int mSeriesId;
    private int mSeason;

    private LoadSeasonEpisodesInteractor.Callback databaseCallback = new LoadSeasonEpisodesInteractor.Callback() {
        @Override
        public void onFinish(List<Episode> episodes) {
            if (mView != null) {
                mView.showEpisodes(episodes);
            }
        }

        @Override
        public void onFail() {

        }
    };

    private TraktSeasonEpisodesInteractor.Callback traktCallback = new TraktSeasonEpisodesInteractor.Callback() {
        @Override
        public void onFinish(List<Episode> episodes) {
            if (mView != null) {
                mView.showEpisodes(episodes);
                mView.hideRefreshAnimation();
            }

            SaveEpisodesInteractor interactor = new SaveEpisodesInteractorImpl(
                    mExecutor, mMainThread, mApplication, episodes, null
            );
            interactor.execute();
        }

        @Override
        public void onFail() {
            if (mView != null) {
                mView.hideRefreshAnimation();
            }
        }
    };

    public SeasonEpisodesPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }

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
                mExecutor, mMainThread, mApplication, mSeriesId, mSeason, traktCallback
        );
        interactor.execute();
    }

    public void loadEpisodes() {
        LoadSeasonEpisodesInteractor interactor = new LoadSeasonEpisodesInteractorImpl(
                mExecutor, mMainThread, mApplication, mSeriesId, mSeason, databaseCallback
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
}
