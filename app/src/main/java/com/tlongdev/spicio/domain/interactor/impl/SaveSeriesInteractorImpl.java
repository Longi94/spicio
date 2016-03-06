package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public class SaveSeriesInteractorImpl extends AbstractInteractor implements SaveSeriesInteractor {

    private static final String LOG_TAG = SaveSeriesInteractorImpl.class.getSimpleName();

    @Inject SeriesDao mSeriesDao;
    @Inject Logger logger;

    private Series mSeries;
    private Callback mCallback;

    public SaveSeriesInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    SpicioApplication app, Series series,
                                    Callback callback) {
        super(threadExecutor, mainThread);
        app.getStorageComponent().inject(this);

        mSeries = series;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");
        // TODO: 2016. 03. 05. send to server, don't insert on failure
        // TODO: 2016. 03. 05. get image links and staffs
        mSeriesDao.insertSeries(mSeries);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFinish();
            }
        });

        logger.debug(LOG_TAG, "finished");
    }
}
