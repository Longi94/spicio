package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.TvdbSearchInteractor;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * Inner Layer, Interactor
 *
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbSearchInteractorImpl extends AbstractInteractor implements TvdbSearchInteractor {

    private static final String LOG_TAG = TvdbSearchInteractorImpl.class.getSimpleName();

    @Inject TvdbRepository mRepository;
    @Inject Logger logger;

    private String mSearchQuery;
    private Callback mCallback;

    public TvdbSearchInteractorImpl(SpicioApplication app, String query, Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);

        mSearchQuery = query;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        List<TvdbSeriesOld> searchResult = mRepository.searchSeries(mSearchQuery);

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
                mCallback.onTvdbSearchFailed();
            }
        });
    }

    private void postResult(final List<TvdbSeriesOld> searchResult) {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTvdbSearchFinish(searchResult);
            }
        });
    }
}
