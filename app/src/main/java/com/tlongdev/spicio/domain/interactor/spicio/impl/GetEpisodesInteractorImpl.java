package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetEpisodesInteractor;
import com.tlongdev.spicio.domain.model.UserEpisodes;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class GetEpisodesInteractorImpl extends AbstractInteractor implements GetEpisodesInteractor {

    private static final String LOG_TAG = GetEpisodesInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject SpicioRepository mRepository;

    private long mUserId;
    private int mSeriesId;
    private Callback mCallback;

    public GetEpisodesInteractorImpl(SpicioApplication application, long userId, int seriesId, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUserId = userId;
        mSeriesId = seriesId;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        UserEpisodes series = mRepository.getEpisodes(mUserId, mSeriesId);

        if (series != null) {
            postFinish(series);
        } else {
            postFail();
        }
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish(final UserEpisodes series) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetEpisodesFinish(series);
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetEpisodesFail();
                }
            });
        }
    }
}