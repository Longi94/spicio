package com.tlongdev.spicio.domain.interactor.trakt.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.trakt.TraktSearchInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
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

    public TraktSearchInteractorImpl(SpicioApplication app, String searchQuery, Callback callback) {
        super(app.getInteractorComponent());
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
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTraktSearchFailed();
            }
        });
    }

    private void postResult(final List<Series> searchResult) {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTraktSearchFinish(searchResult);
            }
        });
    }
}
