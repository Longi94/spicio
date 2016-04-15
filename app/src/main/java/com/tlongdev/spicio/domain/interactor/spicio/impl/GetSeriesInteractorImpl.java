package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetSeriesInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class GetSeriesInteractorImpl extends AbstractInteractor implements GetSeriesInteractor {

    private static final String LOG_TAG = GetSeriesInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject SpicioRepository mRepository;

    private long mUserId;
    private Callback mCallback;

    public GetSeriesInteractorImpl(SpicioApplication application, long userId, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUserId = userId;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        List<Series> series = mRepository.getSeries(mUserId);

        if (series != null) {
            postFinish(series);
        } else {
            postFail();
        }
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish(final List<Series> series) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetSeriesFinish(series);
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetSeriesFail();
                }
            });
        }
    }
}