package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
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
    @Inject TraktRepository mTraktRepository;
    @Inject Logger logger;

    private Series mSeries;
    private Callback mCallback;

    public SaveSeriesInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    SpicioApplication app, Series series,
                                    Callback callback) {
        super(threadExecutor, mainThread);
        app.getInteractorComponent().inject(this);

        mSeries = series;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "getting image links for series");
        Images images = mTraktRepository.getImages(mSeries.getTraktId(), true);

        if (images == null) {
            logger.debug(LOG_TAG, "TraktRepository.getImages returned null");
            postError();
            return;
        }

        mSeries.setImages(images);

        // TODO: 2016. 03. 05. get staffs
        // TODO: 2016. 03. 05. send to server, don't insert on failure
        mSeriesDao.insertSeries(mSeries);

        postFinish();

        logger.debug(LOG_TAG, "finished");
    }

    private void postError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFinish();
            }
        });
    }

    private void postFinish() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFinish();
            }
        });
    }
}
