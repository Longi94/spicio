package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.spicio.GetSeriesInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserSeriesView;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.21.
 */
public class UserSeriesPresenter implements Presenter<UserSeriesView>,GetSeriesInteractor.Callback {

    private SpicioApplication mApplication;

    private UserSeriesView mView;

    public UserSeriesPresenter(SpicioApplication application) {
        mApplication = application;
    }

    @Override
    public void attachView(UserSeriesView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void getSeries(long userId) {
        GetSeriesInteractor interactor = new GetSeriesInteractorImpl(mApplication, userId, this);
        interactor.execute();
    }

    @Override
    public void onGetSeriesFinish(List<Series> series) {
        if (mView != null) {
            mView.showSeries(series);
        }
    }

    @Override
    public void onGetSeriesFail() {
        if (mView != null) {
            mView.showError();
        }
    }
}