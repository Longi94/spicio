package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LoadSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class LoadSeasonEpisodesInteractorImpl extends AbstractInteractor implements LoadSeasonEpisodesInteractor {

    private static final String LOG_TAG = LoadSeasonEpisodesInteractorImpl.class.getSimpleName();

    @Inject EpisodeDao mEpisodeDao;
    @Inject Logger logger;

    private int mSeriesId;
    private int mSeason;
    private Callback mCallback;

    public LoadSeasonEpisodesInteractorImpl(SpicioApplication app, int seriesId, int season,
                                            Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);
        mSeason = season;
        mSeriesId = seriesId;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "loading episodes from database, seriesId: " + mSeriesId + ", season: " + mSeason);
        List<Episode> episodes = mEpisodeDao.getAllEpisodes(mSeriesId, mSeason);
        if (episodes == null) {
            logger.debug(LOG_TAG, "EpisodeDao.getAllEpisodes(int, int) returned null");
            postError();
        } else {
            postFinish(episodes);
        }

        logger.debug(LOG_TAG, "ended");
    }


    private void postError() {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoadSeasonEpisodesFail();
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
                mCallback.onLoadSeasonEpisodesFinish(episodes);
            }
        });
    }
}