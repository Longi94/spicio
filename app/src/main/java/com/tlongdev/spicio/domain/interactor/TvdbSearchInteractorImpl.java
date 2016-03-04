package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

/**
 * Inner Layer, Interactor
 *
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbSearchInteractorImpl extends AbstractInteractor implements TvdbSearchInteractor {

    private String mSearchQuery;
    private Callback mCallback;
    private TvdbRepository mRepository;

    public TvdbSearchInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    String query, Callback callback,
                                    TvdbRepository repository) {
        super(threadExecutor, mainThread);
        mSearchQuery = query;
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {
        List<TvdbSeriesOld> searchResult = mRepository.searchSeries(mSearchQuery);

        if (searchResult == null) {
            postError();
        } else {
            postResult(searchResult);
        }
    }

    private void postError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSearchFailed();
            }
        });
    }

    private void postResult(final List<TvdbSeriesOld> searchResult) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSearchResult(searchResult);
            }
        });
    }
}
