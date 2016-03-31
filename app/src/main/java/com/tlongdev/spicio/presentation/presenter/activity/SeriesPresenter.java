package com.tlongdev.spicio.presentation.presenter.activity;

import android.util.Log;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.storage.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.trakt.TraktFullSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.trakt.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
public class SeriesPresenter implements Presenter<SeriesView>,TraktFullSeriesInteractor.Callback, SaveSeriesInteractor.Callback {

    private static final String LOG_TAG = SeriesPresenter.class.getSimpleName();

    private SeriesView mView;

    private SpicioApplication mApplication;

    private int mSeriesId;

    public SeriesPresenter(SpicioApplication application, int seriesId) {
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
                mApplication, mSeriesId, this
        );
        interactor.execute();
    }

    @Override
    public void onTraktFullSeriesFinish(Series series, List<Season> seasons, List<Episode> episodes) {
        Log.d(LOG_TAG, "finished downloading all series data, inserting into db");

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