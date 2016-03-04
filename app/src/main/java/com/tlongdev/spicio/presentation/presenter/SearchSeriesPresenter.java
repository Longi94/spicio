package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.TraktSearchInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSearchInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.presentation.ui.fragment.SearchSeriesView;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

/**
 * Middle layer, Presenter.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesPresenter extends AbstractPresenter implements Presenter<SearchSeriesView>, TraktSearchInteractor.Callback {

    private SearchSeriesView view;

    private TraktRepository mRepository;

    public SearchSeriesPresenter(Executor executor, MainThread mainThread, TraktRepository repository) {
        super(executor, mainThread);
        mRepository = repository;
    }

    @Override
    public void attachView(SearchSeriesView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public void searchForSeries(String query) {
        TraktSearchInteractor interactor = new TraktSearchInteractorImpl(
                mExecutor,
                mMainThread,
                query,
                mRepository,
                this
        );
        interactor.execute();
    }

    @Override
    public void onSearchResult(List<Series> series) {
        view.showSearchResult(series);
    }

    @Override
    public void onSearchFailed() {
        view.showErrorMessage();
    }
}
