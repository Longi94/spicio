package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.CheckEpisodeInteractor;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public class CheckEpisodeInteractorImpl extends AbstractInteractor implements CheckEpisodeInteractor {

    private static final String LOG_TAG = CheckEpisodeInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject EpisodeDao mEpisodeDao;

    private int mSeriesId;
    private int mEpisodeId;
    private boolean mWatched;
    private Callback mCallback;

    public CheckEpisodeInteractorImpl(SpicioApplication application, int seriesId, int episodeId,
                                      boolean watched, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mSeriesId = seriesId;
        mEpisodeId = episodeId;
        mWatched = watched;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");

        if (mEpisodeDao.setWatched(mSeriesId, mEpisodeId, mWatched)) {
            postFinish();
        } else {
            mLogger.debug(LOG_TAG, "failed tu updated watched column");
            postError();
        }

        mLogger.debug(LOG_TAG, "ended");
    }

    private void postError() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEpisodeCheckFail();
                }
            });
        }
    }
    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEpisodeCheckFinish();
                }
            });
        }
    }
}