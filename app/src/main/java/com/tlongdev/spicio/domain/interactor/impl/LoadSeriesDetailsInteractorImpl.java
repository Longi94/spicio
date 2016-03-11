package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.LoadSeriesDetailsInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class LoadSeriesDetailsInteractorImpl extends AbstractInteractor implements LoadSeriesDetailsInteractor {

    private static final String LOG_TAG = LoadSeriesDetailsInteractorImpl.class.getSimpleName();

    @Inject SeriesDao mSeriesDao;
    @Inject Logger logger;

    private int mSeriesId;
    private Callback mCallback;

    public LoadSeriesDetailsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                           SpicioApplication app, int seriesId,
                                           Callback callback) {
        super(threadExecutor, mainThread);
        app.getInteractorComponent().inject(this);
        mSeriesId = seriesId;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");
        Series series = mSeriesDao.getSeries(mSeriesId);

        if (series == null) {
            logger.debug(LOG_TAG, "SeriesDao.getSeries returned null");
            postError();
            return;
        } else {
            postFinish(series);
        }

        logger.debug(LOG_TAG, "ended");
    }

    private void postFinish(final Series series) {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFinish(series);
            }
        });
    }

    private void postError() {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFail();
            }
        });
    }
}