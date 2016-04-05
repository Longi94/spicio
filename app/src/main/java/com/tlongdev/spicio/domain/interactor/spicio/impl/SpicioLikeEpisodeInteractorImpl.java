package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.SpicioLikeEpisodeInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public class SpicioLikeEpisodeInteractorImpl extends AbstractInteractor implements SpicioLikeEpisodeInteractor {

    private static final String LOG_TAG = SpicioLikeEpisodeInteractorImpl.class.getSimpleName();

    @Inject SpicioRepository mRepository;
    @Inject Logger mLogger;

    private long mUserId;
    private Episode mEpisode;
    private Callback mCallback;

    public SpicioLikeEpisodeInteractorImpl(SpicioApplication application, long userId, Episode episode, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUserId = userId;
        mEpisode = episode;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.verbose(LOG_TAG, "started");
        if (mRepository.likeEpisode(mUserId, mEpisode.getSeriesId(), mEpisode)) {
            postSuccess();
        } else {
            postFail();
        }
        mLogger.verbose(LOG_TAG, "ended");
    }

    private void postSuccess() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSpicioLikeFinish();
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSpicioLikeFail();
                }
            });
        }
    }
}