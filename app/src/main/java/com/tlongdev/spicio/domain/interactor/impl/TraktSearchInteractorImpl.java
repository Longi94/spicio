package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSearchInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class TraktSearchInteractorImpl extends AbstractInteractor implements TraktSearchInteractor {

    private static final String LOG_TAG = TraktSearchInteractorImpl.class.getSimpleName();

    @Inject TraktRepository mRepository;
    @Inject Logger logger;

    private String mSearchQuery;
    private Callback mCallback;

    public TraktSearchInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     SpicioApplication app, String searchQuery,
                                     Callback callback) {
        super(threadExecutor, mainThread);
        app.getInteractorComponent().inject(this);

        mSearchQuery = searchQuery;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        List<Series> searchResult = mRepository.searchSeries(mSearchQuery);

        if (searchResult == null) {
            postError();
        } else {
            postResult(searchResult);
        }

        logger.debug(LOG_TAG, "finished");
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
