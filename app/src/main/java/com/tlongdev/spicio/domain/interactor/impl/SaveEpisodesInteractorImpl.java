package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.SaveEpisodesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class SaveEpisodesInteractorImpl extends AbstractInteractor implements SaveEpisodesInteractor {

    private static final String LOG_TAG = SaveEpisodesInteractorImpl.class.getSimpleName();

    @Inject EpisodeDao mEpisodeDao;
    @Inject Logger logger;

    private List<Episode> mEpisodes;
    private Callback mCallback;

    public SaveEpisodesInteractorImpl(SpicioApplication app, List<Episode> episodes, Callback callback) {
        super(app.getInteractorComponent());
        app.getInteractorComponent().inject(this);
        mEpisodes = episodes;
        mCallback = callback;
    }

    @Override
    public void run() {
        logger.debug(LOG_TAG, "started");

        int episodesInserted = mEpisodeDao.insertAllEpisodes(mEpisodes);
        if (episodesInserted != mEpisodes.size()) {
            logger.warn(LOG_TAG, "episodes to insert: " + mEpisodes.size() + ", actually inserted: " + episodesInserted);
        }

        postFinish();
        logger.debug(LOG_TAG, "ended");
    }

    private void postFinish() {
        if (mCallback != null) {
            mCallback.onSaveEpisodesFinish();
        }
    }
}