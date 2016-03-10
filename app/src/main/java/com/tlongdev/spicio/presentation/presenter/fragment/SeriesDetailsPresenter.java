package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.LoadSeriesDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.AbstractPresenter;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeriesDetailsView;
import com.tlongdev.spicio.threading.MainThread;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class SeriesDetailsPresenter extends AbstractPresenter implements Presenter<SeriesDetailsView>,LoadSeriesDetailsInteractor.Callback {

    private SeriesDetailsView mView;

    public SeriesDetailsPresenter(Executor executor, MainThread mainThread) {
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

    public void loadSeasonDetails(int seriesId) {
        LoadSeriesDetailsInteractor interactor = new LoadSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), seriesId, this
        );
        interactor.execute();
    }

    @Override
    public void onFinish(Series series) {
        if (mView != null) {
            mView.showDetails(series);
        }
    }

    @Override
    public void onFail() {

    }
}
