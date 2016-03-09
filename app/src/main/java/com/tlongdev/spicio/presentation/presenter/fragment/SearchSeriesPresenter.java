package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.TraktSearchInteractor;
import com.tlongdev.spicio.domain.interactor.impl.TraktSearchInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.presenter.AbstractPresenter;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SearchSeriesView;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

/**
 * Middle layer, Presenter.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesPresenter extends AbstractPresenter implements Presenter<SearchSeriesView>, TraktSearchInteractor.Callback {

    private SearchSeriesView mView;

    public SearchSeriesPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }

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
                mExecutor,
                mMainThread,
                mView.getSpicioApplication(),
                query,
                this
        );
        interactor.execute();
    }

    @Override
    public void onSearchResult(List<Series> series) {
        mView.showSearchResult(series);
    }

    @Override
    public void onSearchFailed() {
        mView.showErrorMessage();
    }
}
