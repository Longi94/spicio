package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.TraktSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class TraktSeasonEpisodesInteractorImpl extends AbstractInteractor implements TraktSeasonEpisodesInteractor {

    private static final String LOG_TAG = TraktSeasonEpisodesInteractorImpl.class.getSimpleName();

    @Inject TraktRepository mRepository;
    @Inject Logger logger;

    private int mSeriesId;
    private int mSeason;
    private Callback mCallback;

    public TraktSeasonEpisodesInteractorImpl(SpicioApplication app, int seriesId, int season,
                                             Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);
        mSeriesId = seriesId;
        mSeason = season;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "getting list of episodes with images");
        List<Episode> episodesImages = mRepository.getEpisodeImages(mSeriesId, mSeason);
        if (episodesImages == null) {
            logger.debug(LOG_TAG, "TraktRepository.getEpisodeImages returned null");
            postError();
            return;
        }

        logger.debug(LOG_TAG, "getting list of episodes with images");
        List<Episode> episodes = mRepository.getSeasonEpisodes(mSeriesId, mSeason);
        if (episodes == null) {
            logger.debug(LOG_TAG, "TraktRepository.getEpisodeImages returned null");
            postError();
            return;
        }

        for (int i = 0; i < episodes.size(); i++) {
            episodes.get(i).setImages(episodesImages.get(i).getImages());
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
                mCallback.onTraktSeasonEpisodesFail();
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
                mCallback.onTraktSeasonEpisodesFinish(episodes);
            }
        });
    }
}