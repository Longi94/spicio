package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.domain.interactor.TraktSearchInteractor;
import com.tlongdev.spicio.domain.interactor.impl.TraktSearchInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SearchSeriesView;

import java.util.List;

/**
 * Middle layer, Presenter.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesPresenter implements Presenter<SearchSeriesView>, TraktSearchInteractor.Callback {

    private SearchSeriesView mView;

    @Override
    public void attachView(SearchSeriesView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void searchForSeries(String query) {
        TraktSearchInteractor interactor = new TraktSearchInteractorImpl(
                mView.getSpicioApplication(), query, this
        );
        interactor.execute();
    }

    @Override
    public void onTraktSearchFinish(List<Series> series) {
        if (mView != null) {
            mView.showSearchResult(series);
        }
    }

    @Override
    public void onTraktSearchFailed() {
        if (mView != null) {
            mView.showErrorMessage();
        }
    }
}
