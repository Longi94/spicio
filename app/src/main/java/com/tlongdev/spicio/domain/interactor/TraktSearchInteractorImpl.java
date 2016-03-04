package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class TraktSearchInteractorImpl extends AbstractInteractor implements TraktSearchInteractor {

    private String mSearchQuery;
    private TraktRepository mRepository;
    private Callback mCallback;

    public TraktSearchInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     String searchQuery, TraktRepository repository,
                                     Callback callback) {
        super(threadExecutor, mainThread);
        mSearchQuery = searchQuery;
        mRepository = repository;
        mCallback = callback;
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
