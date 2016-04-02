package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SkipEpisodeInteractor;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.02.
 */
public class SkipEpisodeInteractorImpl extends AbstractInteractor implements SkipEpisodeInteractor {

    private static final String LOG_TAG = SkipEpisodeInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject EpisodeDao mEpisodeDao;

    private int mSeriesId;
    private int mEpisodeId;
    private boolean mSkipped;
    private Callback mCallback;

    public SkipEpisodeInteractorImpl(SpicioApplication application, int seriesId, int episodeId,
                                     boolean skipped, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mSeriesId = seriesId;
        mEpisodeId = episodeId;
        mSkipped = skipped;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");

        if (mEpisodeDao.setSkipped(mSeriesId, mEpisodeId, mSkipped)) {
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
                    mCallback.onEpisodeSkipFail();
                }
            });
        }
    }
    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEpisodeSkipFinish();
                }
            });
        }
    }
}