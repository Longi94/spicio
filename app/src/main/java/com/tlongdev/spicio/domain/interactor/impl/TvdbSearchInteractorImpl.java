package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.TvdbSearchInteractor;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

import javax.inject.Inject;

/**
 * Inner Layer, Interactor
 *
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbSearchInteractorImpl extends AbstractInteractor implements TvdbSearchInteractor {

    @Inject TvdbRepository mRepository;

    private String mSearchQuery;
    private Callback mCallback;

    public TvdbSearchInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    SpicioApplication app,
                                    String query, Callback callback) {
        super(threadExecutor, mainThread);
        app.getNetworkComponent().inject(this);

        mSearchQuery = query;
        mCallback = callback;
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
