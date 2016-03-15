package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.DeleteAllDataInteractor;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
public class DeleteAllDataInteractorImpl extends AbstractInteractor implements DeleteAllDataInteractor {

    private static final String LOG_TAG = DeleteAllDataInteractorImpl.class.getSimpleName();

    @Inject SeriesDao mSeriesDao;
    @Inject Logger mLogger;

    private Callback mCallback;

    public DeleteAllDataInteractorImpl(SpicioApplication application, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");

        mSeriesDao.deleteAllData();

        postFinish();
        mLogger.debug(LOG_TAG, "ended");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onFinish();
                }
            });
        }
    }
}