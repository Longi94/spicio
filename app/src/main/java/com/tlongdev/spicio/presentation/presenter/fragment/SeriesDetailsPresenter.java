package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.storage.LoadSeriesDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeriesDetailsView;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class SeriesDetailsPresenter implements Presenter<SeriesDetailsView>,LoadSeriesDetailsInteractor.Callback {

    private SeriesDetailsView mView;

    private SpicioApplication mApplication;

    public SeriesDetailsPresenter(SpicioApplication application) {
        mApplication = application;
    }

    @Override
    public void attachView(SeriesDetailsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadSeasonDetails(int seriesId) {
        LoadSeriesDetailsInteractor interactor = new LoadSeriesDetailsInteractorImpl(
                mApplication, seriesId, this
        );
        interactor.execute();
    }

    @Override
    public void onLoadSeriesDetailsFinish(Series series) {
        if (mView != null) {
            mView.showDetails(series);
        }
    }

    @Override
    public void onLoadSeriesDetailsFail() {

    }
}
