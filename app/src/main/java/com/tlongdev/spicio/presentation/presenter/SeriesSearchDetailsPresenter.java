package com.tlongdev.spicio.presentation.presenter;

import android.util.Log;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.TraktFullSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSeriesDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.view.activity.SeriesDetailsView;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class SeriesSearchDetailsPresenter extends AbstractPresenter implements Presenter<SeriesDetailsView>,TraktSeriesDetailsInteractor.Callback, SaveSeriesInteractor.Callback, TraktFullSeriesInteractor.Callback {

    private static final String LOG_TAG = SeriesSearchDetailsPresenter.class.getSimpleName();

    private SeriesDetailsView mView;

    public SeriesSearchDetailsPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }

    @Override
    public void attachView(SeriesDetailsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadDetails(int traktId) {
        TraktSeriesDetailsInteractor interactor = new TraktSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), traktId, this
        );

        interactor.execute();
    }

    @Override
    public void onResult(Series series) {
        Log.d(LOG_TAG, "onResult() called");
        mView.showDetails(series);
    }

    @Override
    public void onFinish(Series series, List<Season> seasons, List<Episode> episodes) {
        Log.d(LOG_TAG, "finished downloading all series data, inserting into db");

        SaveSeriesInteractor interactor = new SaveSeriesInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), series, seasons, episodes, this
        );
        interactor.execute();
    }

    @Override
    public void onFail() {
        Log.d(LOG_TAG, "onFail() called");
        mView.reportError();
    }

    public void saveSeries(Series series) {
        Log.d(LOG_TAG, "saveSeries() called");

        TraktFullSeriesInteractor interactor = new TraktFullSeriesInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), series, this
        );
        interactor.execute();
    }

    @Override
    public void onFinish() {
        Log.d(LOG_TAG, "onFinish() called with: " + "");
        mView.onSeriesSaved();
    }
}
