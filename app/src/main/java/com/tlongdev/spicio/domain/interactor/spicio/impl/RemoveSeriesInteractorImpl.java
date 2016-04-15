package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.RemoveSeriesInteractor;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class RemoveSeriesInteractorImpl extends AbstractInteractor implements RemoveSeriesInteractor {

    private static final String LOG_TAG = RemoveSeriesInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject SpicioRepository mRepository;

    private long mUserId;
    private int mSeriesId;
    private Callback mCallback;

    public RemoveSeriesInteractorImpl(SpicioApplication application, long userId, int seriesId, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUserId = userId;
        mSeriesId = seriesId;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");

        if (mRepository.removeSeries(mUserId, mSeriesId)) {
            postFinish();
        } else {
            postFail();
        }

        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onRemoveSeriesFinish();
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onRemoveSeriesFail();
                }
            });
        }
    }
}