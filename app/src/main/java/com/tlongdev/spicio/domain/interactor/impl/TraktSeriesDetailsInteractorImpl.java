package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSeriesDetailsInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class TraktSeriesDetailsInteractorImpl extends AbstractInteractor implements TraktSeriesDetailsInteractor {

    private static final String LOG_TAG = TraktSeriesDetailsInteractorImpl.class.getSimpleName();

    @Inject TraktRepository mRepository;
    @Inject Logger logger;

    private Callback mCallback;
    private int mTraktId;

    public TraktSeriesDetailsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                            SpicioApplication app, int traktId,
                                            Callback callback) {
        super(threadExecutor, mainThread);
        app.getNetworkComponent().inject(this);

        mCallback = callback;
        mTraktId = traktId;
    }

    @Override
    public void run() {
        logger.verbose(LOG_TAG, "started");

        Series series = mRepository.getSeriesDetails(mTraktId);

        if (series == null) {
            postError();
        } else {
            postResult(series);
        }

        logger.verbose(LOG_TAG, "finished");
    }


    private void postError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFail();
            }
        });
    }

    private void postResult(final Series series) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onResult(series);
            }
        });
    }
}
