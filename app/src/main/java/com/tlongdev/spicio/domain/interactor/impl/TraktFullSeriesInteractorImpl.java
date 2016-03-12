package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.TraktFullSeriesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 08.
 */
public class TraktFullSeriesInteractorImpl extends AbstractInteractor implements TraktFullSeriesInteractor {

    private static final String LOG_TAG = TraktFullSeriesInteractorImpl.class.getSimpleName();

    @Inject TraktRepository mTraktRepository;
    @Inject Logger logger;

    private Callback mCallback;
    private Series mSeries;

    public TraktFullSeriesInteractorImpl(SpicioApplication app, Series series,  Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);
        mCallback = callback;
        mSeries = series;
    }

    @Override
    public void run() {

        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "getting image links for series");
        Images images = mTraktRepository.getImages(mSeries.getTraktId());

        if (images == null) {
            logger.debug(LOG_TAG, "TraktRepository.getImages returned null");
            postError();
            return;
        }

        mSeries.setImages(images);

        logger.debug(LOG_TAG, "getting seasons for series");
        List<Season> seasons = mTraktRepository.getSeasons(mSeries.getTraktId());
        if (seasons == null) {
            logger.debug(LOG_TAG, "TraktRepository.getSeasons returned null");
            postError();
            return;
        }

        List<Episode> episodes = mTraktRepository.getEpisodesForSeries(mSeries.getTraktId());
        if (episodes == null) {
            logger.debug(LOG_TAG, "TraktRepository.getEpisodesForSeries returned null");
            postError();
            return;
        }

        postFinish(mSeries, seasons, episodes);

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