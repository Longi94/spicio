package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SaveSeriesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public class SaveSeriesInteractorImpl extends AbstractInteractor implements SaveSeriesInteractor {

    private static final String LOG_TAG = SaveSeriesInteractorImpl.class.getSimpleName();

    @Inject SeriesDao mSeriesDao;
    @Inject EpisodeDao mEpisodeDao;
    @Inject Logger logger;

    private Series mSeries;
    private List<Season> mSeasons;
    private List<Episode> mEpisodes;
    private Callback mCallback;

    public SaveSeriesInteractorImpl(SpicioApplication app, Series series, List<Season> seasons,
                                    List<Episode> episodes, Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);

        mSeries = series;
        mCallback = callback;
        mSeasons = seasons;
        mEpisodes = episodes;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "inserting episodes into database");
        mEpisodeDao.insertAllEpisodes(mEpisodes);

        logger.debug(LOG_TAG, "inserting seasons into database");
        mEpisodeDao.insertAllSeasons(mSeasons);

        logger.debug(LOG_TAG, "inserting the series");
        mSeriesDao.insertSeries(mSeries);

        postFinish();

        logger.debug(LOG_TAG, "finished");
    }

    private void postError() {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSaveSeriesFail();
            }
        });
    }

    private void postFinish() {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSaveSeriesFinish();
            }
        });
    }
}
