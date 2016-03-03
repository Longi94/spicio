package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.TvdbSearchInteractor;
import com.tlongdev.spicio.domain.interactor.TvdbSearchInteractorInstance;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.presentation.ui.fragment.SearchSeriesView;

import java.util.List;

/**
 * Middle layer, Presenter.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesPresenter extends AbstractPresenter implements Presenter<SearchSeriesView>, TvdbSearchInteractor.Callback {

    private SearchSeriesView view;

    private TvdbRepository mRepository;

    public SearchSeriesPresenter(Executor executor, MainThread mainThread, TvdbRepository repository) {
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
        TvdbSearchInteractor interactor = new TvdbSearchInteractorInstance(
                mExecutor,
                mMainThread,
                query,
                this,
                mRepository
        );
        interactor.execute();
    }

    @Override
    public void onSearchResult(List<TvdbSeriesOld> series) {
        view.showSearchResult(series);
    }

    @Override
    public void onSearchFailed() {
        view.showErrorMessage();
    }
}
