package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LikeEpisodeInteractor;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public class LikeEpisodeInteractorImpl extends AbstractInteractor implements LikeEpisodeInteractor {

    private static final String LOG_TAG = LikeEpisodeInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject EpisodeDao mEpisodeDao;

    private int mEpisodeId;
    private boolean mLiked;
    private Callback mCallback;

    public LikeEpisodeInteractorImpl(SpicioApplication application, int episodeId, boolean liked,
                                     Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mEpisodeId = episodeId;
        mLiked = liked;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");

        int rowsUpdated = mEpisodeDao.setLiked(mEpisodeId, mLiked);
        if (rowsUpdated == 0) {
            mLogger.debug(LOG_TAG, "failed tu updated liked column");
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
                    mCallback.onEpisodeLikeFail();
                }
            });
        }
    }
    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onEpisodeLikeFinish();
                }
            });
        }
    }
}