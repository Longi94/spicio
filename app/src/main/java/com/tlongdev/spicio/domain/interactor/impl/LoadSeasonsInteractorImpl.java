package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.LoadSeasonsInteractor;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.util.Logger;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Response;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class LoadSeasonsInteractorImpl extends AbstractInteractor implements LoadSeasonsInteractor {

    private static final String LOG_TAG = LoadSeasonsInteractorImpl.class.getSimpleName();

    @Inject EpisodeDao mEpisodeDao;
    @Inject Logger logger;

    private Series mSeries;
    private Callback mCallback;

    public LoadSeasonsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                     SpicioApplication app, Series series,
                                     Callback callback) {
        super(threadExecutor, mainThread);
        app.getInteractorComponent().inject(this);
        mSeries = series;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        logger.debug(LOG_TAG, "querying seasons for " + mSeries.getTitle());
        List<Season> seasons = mEpisodeDao.getAllSeasons(mSeries.getTraktId());

        if (seasons == null) {
            postError();
            return;
        }

        postFinish(seasons);
        logger.debug(LOG_TAG, "ended");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
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
