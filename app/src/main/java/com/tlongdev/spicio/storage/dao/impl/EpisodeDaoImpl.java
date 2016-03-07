package com.tlongdev.spicio.storage.dao.impl;

import android.content.ContentResolver;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.storage.dao.EpisodeDao;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
public class EpisodeDaoImpl implements EpisodeDao {

    @Inject ContentResolver mContentResolver;

    public EpisodeDaoImpl(SpicioApplication application) {
        application.getStorageComponent().inject(this);
    }

    @Override
    public Episode getEpisode(int episodeId) {
        return null;
    }

    @Override
    public List<Episode> getAllEpisodes() {
        return null;
    }

    @Override
    public List<Episode> getAllEpisodes(int seriesId) {
        return null;
    }

    @Override
    public List<Episode> getAllEpisodes(int seriesId, int season) {
        return null;
    }

    @Override
    public List<Season> getAllSeasons(int seriesId) {
        return null;
    }

    @Override
    public void insertAllEpisodes(List<Episode> episodes) {

    }

    @Override
    public void deleteAllEpisodes(int seriesId) {

    }

    @Override
    public void deleteAllEpisodes() {

    }

    @Override
    public boolean isWatched(int episodeId) {
        return false;
    }

    @Override
    public void setWatched(int episodeId, boolean watched) {

    }

    @Override
    public List<Episode> getUpcomingEpisodes() {
        return null;
    }

    @Override
    public List<Episode> getEpisodeHistory(boolean includeUnwatched) {
        return null;
    }
}
