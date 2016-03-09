package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.LoadSeasonsInteractor;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.threading.MainThread;
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

    public LoadSeasonsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     SpicioApplication app, int seriesId,
                                     Callback callback) {
        super(threadExecutor, mainThread);
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
        }

        postFinish(seasons);
        logger.debug(LOG_TAG, "ended");
    }

    private void postError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFail();
            }
        });
    }

    private void postFinish(final List<Season> seasons) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFinish(seasons);
            }
        });
    }
}
