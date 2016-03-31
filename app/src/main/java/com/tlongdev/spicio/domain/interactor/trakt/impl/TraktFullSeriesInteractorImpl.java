package com.tlongdev.spicio.domain.interactor.trakt.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.trakt.TraktFullSeriesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 08.
 */
public class TraktFullSeriesInteractorImpl extends AbstractInteractor implements TraktFullSeriesInteractor {

    private static final String LOG_TAG = TraktFullSeriesInteractorImpl.class.getSimpleName();

    @Inject TraktRepository mRepository;
    @Inject Logger logger;

    private Callback mCallback;
    private int mSeriesId;

    public TraktFullSeriesInteractorImpl(SpicioApplication app, int seriesId,  Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);
        mCallback = callback;
        mSeriesId = seriesId;
    }

    @Override
    public void run() {

        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "getting series details");
        Series series = mRepository.getSeriesDetails(mSeriesId);

        if (series == null) {
            logger.debug(LOG_TAG, "TraktRepository.getSeriesDetails returned null");
            postError();
            return;
        }

        logger.debug(LOG_TAG, "getting image links for series");
        Images images = mRepository.getImages(mSeriesId);

        if (images == null) {
            logger.debug(LOG_TAG, "TraktRepository.getImages returned null");
            postError();
            return;
        }

        series.setImages(images);

        logger.debug(LOG_TAG, "getting seasons for series");
        List<Season> seasons = mRepository.getSeasons(mSeriesId);
        if (seasons == null) {
            logger.debug(LOG_TAG, "TraktRepository.getSeasons returned null");
            postError();
            return;
        }

        List<Episode> episodes = new LinkedList<>();
        for (Season season : seasons) {
            logger.debug(LOG_TAG, "getting list of episodes with images for season " + season.getNumber());
            List<Episode> episodesImages = mRepository.getEpisodeImages(mSeriesId, season.getNumber());
            if (episodesImages == null) {
                logger.debug(LOG_TAG, "TraktRepository.getEpisodeImages returned null");
                postError();
                return;
            }

            logger.debug(LOG_TAG, "getting list of episodes for season " + season.getNumber());
            List<Episode> tempEpisodes = mRepository.getSeasonEpisodes(mSeriesId, season.getNumber());
            if (tempEpisodes == null) {
                logger.debug(LOG_TAG, "TraktRepository.getEpisodeImages returned null");
                postError();
                return;
            }

            for (int i = 0; i < tempEpisodes.size(); i++) {
                tempEpisodes.get(i).setImages(episodesImages.get(i).getImages());
            }

            episodes.addAll(tempEpisodes);
        }

        postFinish(series, seasons, episodes);

        logger.debug(LOG_TAG, "finished");
    }

    private void postFinish(final Series series, final List<Season> seasons, final List<Episode> episodes) {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTraktFullSeriesFinish(series, seasons, episodes);
            }
        });
    }

    private void postError() {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onTraktFullSeriesFail();
            }
        });
    }
}