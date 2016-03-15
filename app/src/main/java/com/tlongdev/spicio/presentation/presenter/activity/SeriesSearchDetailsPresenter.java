package com.tlongdev.spicio.presentation.presenter.activity;

import android.util.Log;

import com.tlongdev.spicio.domain.interactor.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.TraktFullSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSeriesDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesSearchDetailsView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class SeriesSearchDetailsPresenter implements Presenter<SeriesSearchDetailsView>,
        TraktSeriesDetailsInteractor.Callback, SaveSeriesInteractor.Callback,
        TraktFullSeriesInteractor.Callback {

    private static final String LOG_TAG = SeriesSearchDetailsPresenter.class.getSimpleName();

    private SeriesSearchDetailsView mView;

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
                mView.getSpicioApplication(), traktId, this
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
        Log.d(LOG_TAG, "finished downloading all series data, inserting into db");

        SaveSeriesInteractor interactor = new SaveSeriesInteractorImpl(
                mView.getSpicioApplication(), series, seasons, episodes, this
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
        Log.d(LOG_TAG, "saveSeries() called");

        mView.showLoading();

        TraktFullSeriesInteractor interactor = new TraktFullSeriesInteractorImpl(
                mView.getSpicioApplication(), series.getTraktId(), this
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
}
