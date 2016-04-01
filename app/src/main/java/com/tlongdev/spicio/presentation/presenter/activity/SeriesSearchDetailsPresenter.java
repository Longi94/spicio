package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.spicio.AddSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.AddSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.trakt.TraktFullSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.trakt.TraktSeriesDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.trakt.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.trakt.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesSearchDetailsView;
import com.tlongdev.spicio.util.Logger;
import com.tlongdev.spicio.util.ProfileManager;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class SeriesSearchDetailsPresenter implements Presenter<SeriesSearchDetailsView>,
        TraktSeriesDetailsInteractor.Callback, SaveSeriesInteractor.Callback,
        TraktFullSeriesInteractor.Callback, AddSeriesInteractor.Callback {

    private static final String LOG_TAG = SeriesSearchDetailsPresenter.class.getSimpleName();

    @Inject ProfileManager mProfileManager;
    @Inject Logger mLogger;

    private SeriesSearchDetailsView mView;

    private SpicioApplication mApplication;

    private Series mSeries;
    private List<Season> mSeasons;
    private List<Episode> mEpisodes;

    public SeriesSearchDetailsPresenter(SpicioApplication application) {
        application.getPresenterComponent().inject(this);
        mApplication = application;
    }

    @Override
    public void attachView(SeriesSearchDetailsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadDetails(int traktId) {
        TraktSeriesDetailsInteractor interactor = new TraktSeriesDetailsInteractorImpl(
                mApplication, traktId, this
        );
        interactor.execute();
    }

    @Override
    public void onTraktSeriesDetailsFinish(Series series) {
        if (mView != null) {
            mView.showDetails(series);
        }
    }

    @Override
    public void onTraktSeriesDetailsFail() {
        // TODO: 2016. 03. 11.  
    }

    @Override
    public void onTraktFullSeriesFinish(Series series, List<Season> seasons, List<Episode> episodes) {
        mLogger.verbose(LOG_TAG, "finished downloading all series data, inserting into db");

        mSeries = series;
        mSeasons = seasons;
        mEpisodes = episodes;

        mLogger.verbose(LOG_TAG, "sending series to server");
        AddSeriesInteractor interactor = new AddSeriesInteractorImpl(
                mApplication, mProfileManager.getId(), mSeries, this
        );
        interactor.execute();
    }

    @Override
    public void onTraktFullSeriesFail() {
        if (mView != null) {
            mView.reportError();
        }
    }

    public void saveSeries(Series series) {
        mLogger.verbose(LOG_TAG, "saveSeries() called");

        mView.showLoading();

        TraktFullSeriesInteractor interactor = new TraktFullSeriesInteractorImpl(
                mApplication, series.getTraktId(), true, this
        );
        interactor.execute();
    }

    @Override
    public void onSaveSeriesFinish() {
        if (mView != null) {
            mView.onSeriesSaved();
            mView.hideLoading();
        }
    }

    @Override
    public void onSaveSeriesFail() {
        // TODO: 2016. 03. 11.
    }

    @Override
    public void onAddSeriesFinish() {
        mLogger.verbose(LOG_TAG, "sent series to server");
        SaveSeriesInteractor interactor = new SaveSeriesInteractorImpl(
                mApplication, mSeries, mSeasons, mEpisodes, this
        );
        interactor.execute();
    }

    @Override
    public void onAddSeriesFail() {

    }
}
