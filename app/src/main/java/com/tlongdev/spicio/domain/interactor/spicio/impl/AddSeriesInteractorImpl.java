package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.AddSeriesInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 31.
 */
public class AddSeriesInteractorImpl extends AbstractInteractor implements AddSeriesInteractor {

    private static final String LOG_TAG = AddSeriesInteractorImpl.class.getSimpleName();

    @Inject SpicioRepository mRepository;
    @Inject Logger mLogger;

    private long mUserId;
    private Series mSeries;
    private Callback mCallback;

    public AddSeriesInteractorImpl(SpicioApplication application, long userId, Series series, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUserId = userId;
        mSeries = series;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.verbose(LOG_TAG, "started");

        if (mRepository.addSeries(mUserId, mSeries)) {
            postFinish();
        } else {
            postError();
        }

        mLogger.verbose(LOG_TAG, "ended");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAddSeriesFinish();
                }
            });
        }
    }

    private void postError() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAddSeriesFail();
                }
            });
        }
    }
}