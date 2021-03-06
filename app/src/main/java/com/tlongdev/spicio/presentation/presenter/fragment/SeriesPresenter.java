package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.domain.interactor.LoadAllSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadAllSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeriesView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 06.
 */
public class SeriesPresenter implements Presenter<SeriesView>,LoadAllSeriesInteractor.Callback {

    private SeriesView mView;

    @Override
    public void attachView(SeriesView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadSeries() {
        LoadAllSeriesInteractor interactor = new LoadAllSeriesInteractorImpl(
                mView.getSpicioApplication(), this
        );
        interactor.execute();
    }

    @Override
    public void onLoadAllSeriesFinish(List<Series> series) {
        if (mView != null) {
            mView.showSeries(series);
        }
    }

    @Override
    public void onLoadAllSeriesFail() {
        // TODO: 2016. 03. 07.
    }
}
