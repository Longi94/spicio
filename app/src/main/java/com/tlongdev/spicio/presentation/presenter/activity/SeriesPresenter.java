package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.storage.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.trakt.TraktFullSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.trakt.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesView;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
public class SeriesPresenter implements Presenter<SeriesView>,TraktFullSeriesInteractor.Callback, SaveSeriesInteractor.Callback {

    private static final String LOG_TAG = SeriesPresenter.class.getSimpleName();

    @Inject Logger mLogger;

    private SeriesView mView;

    private SpicioApplication mApplication;

    private int mSeriesId;

    public SeriesPresenter(SpicioApplication application, int seriesId) {
        application.getPresenterComponent().inject(this);
        mApplication = application;
        mSeriesId = seriesId;
    }

    @Override
    public void attachView(SeriesView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void refreshSeries() {
        mView.showLoading();
        TraktFullSeriesInteractor interactor = new TraktFullSeriesInteractorImpl(
                mApplication, mSeriesId, true, this
        );
        interactor.execute();
    }

    @Override
    public void onTraktFullSeriesFinish(Series series, List<Season> seasons, List<Episode> episodes) {
        mLogger.verbose(LOG_TAG, "finished downloading all series data, inserting into db");

        SaveSeriesInteractor interactor = new SaveSeriesInteractorImpl(
                mApplication, series, seasons, episodes, this
        );
        interactor.execute();

    }

    @Override
    public void onTraktFullSeriesFail() {
        if (mView != null) {
            mView.hideLoading();
        }
    }

    @Override
    public void onSaveSeriesFinish() {
        if (mView != null) {
            mView.reloadData();
            mView.hideLoading();
        }
    }

    @Override
    public void onSaveSeriesFail() {
        if (mView != null) {
            mView.hideLoading();
        }
    }
}