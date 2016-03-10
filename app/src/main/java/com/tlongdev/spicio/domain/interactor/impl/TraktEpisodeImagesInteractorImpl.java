package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.TraktEpisodeImagesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class TraktEpisodeImagesInteractorImpl extends AbstractInteractor implements TraktEpisodeImagesInteractor {

    private static final String LOG_TAG = TraktEpisodeImagesInteractorImpl.class.getSimpleName();

    @Inject TraktRepository mRepository;
    @Inject Logger logger;

    private int mSeriesId;
    private int mSeason;
    private Callback mCallback;

    public TraktEpisodeImagesInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                            SpicioApplication app, int seriesId, int season,
                                            Callback callback) {
        super(threadExecutor, mainThread);
        app.getInteractorComponent().inject(this);
        mSeriesId = seriesId;
        mSeason = season;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "getting list of episodes with images");
        List<Episode> episodes = mRepository.getEpisodeImages(mSeriesId, mSeason);
        if (episodes == null) {
            logger.debug(LOG_TAG, "TraktRepository.getEpisodeImages returned null");
            postError();
            return;
        }

        postFinish(episodes);
        logger.debug(LOG_TAG, "ended");
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
    private void postFinish(final List<Episode> episodes) {
        if (mCallback == null) {
            return;
        }
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFinish(episodes);
            }
        });
    }
}