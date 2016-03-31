package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LoadSeasonsInteractor;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class LoadSeasonsInteractorImpl extends AbstractInteractor implements LoadSeasonsInteractor {

    private static final String LOG_TAG = LoadSeasonsInteractorImpl.class.getSimpleName();

    @Inject EpisodeDao mEpisodeDao;
    @Inject Logger logger;

    private int mSeriesId;
    private Callback mCallback;

    public LoadSeasonsInteractorImpl(SpicioApplication app, int seriesId, Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);
        mSeriesId = seriesId;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "querying seasons for " + mSeriesId);
        List<Season> seasons = mEpisodeDao.getAllSeasons(mSeriesId);

        if (seasons == null) {
            postError();
            return;
        } else {
            postFinish(seasons);
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
                mCallback.onLoadSeasonsFail();
            }
        });
    }

    private void postFinish(final List<Season> seasons) {
        if (mCallback == null) {
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoadSeasonsFinish(seasons);
            }
        });
    }
}
