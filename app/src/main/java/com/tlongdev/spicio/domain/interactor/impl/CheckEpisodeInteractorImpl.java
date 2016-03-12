package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.CheckEpisodeInteractor;
import com.tlongdev.spicio.domain.model.Watched;
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

    private int mEpisodeId;
    private int mWatched;
    private Callback mCallback;

    public CheckEpisodeInteractorImpl(SpicioApplication application, int episodeId,
                                      @Watched.Enum int watched, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mEpisodeId = episodeId;
        mWatched = watched;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");

        int rowsUpdated = mEpisodeDao.setWatched(mEpisodeId, mWatched);
        if (rowsUpdated == 0) {
            mLogger.debug(LOG_TAG, "failed tu updated watched column");
            postError();
        } else {
            postFinish();
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