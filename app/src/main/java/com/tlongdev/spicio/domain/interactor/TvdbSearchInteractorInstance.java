package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TvdbRepository;

import java.util.List;

/**
 * Inner Layer, Interactor
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbSearchInteractorInstance extends AbstractInteractor implements TvdbSearchInteractor {

    private String mSearchQuery;
    private TvdbSearchInteractor.Callback mCallback;
    private TvdbRepository mRepository;

    public TvdbSearchInteractorInstance(Executor threadExecutor, MainThread mainThread,
                                        String query, TvdbSearchInteractor.Callback callback,
                                        TvdbRepository repository) {
        super(threadExecutor, mainThread);
        mSearchQuery = query;
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {
        List<Series> searchResult = mRepository.searchSeries(mSearchQuery);

        if (searchResult == null) {
            postError();
        }

        postResult(searchResult);
    }

    private void postError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSearchFailed();
            }
        });
    }

    private void postResult(final List<Series> searchResult) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSearchResult(searchResult);
            }
        });
    }
}
