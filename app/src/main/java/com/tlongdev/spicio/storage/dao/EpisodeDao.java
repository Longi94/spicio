package com.tlongdev.spicio.storage.dao;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;

import java.util.List;

/**
 * Middle Layer, Converter.
 *
 * @author Long
 * @since 2016. 02. 29.
 */
public interface EpisodeDao {

    String COLUMN_WATCH_COUNT = "watch_count";
    String COLUMN_SKIP_COUNT = "skip_count";
    
    /**
     * Get a single episode from the database.
     *
     * @param episodeId the id of the episode
     * @return the episode
     */
    Episode getEpisode(int episodeId);

    /**
     * Get all the episodes from the database
     *
     * @return all the episodes
     */
    List<Episode> getAllEpisodes();

    /**
     * Get all the episode of a season of a series.
     *
     * @param seriesId the id of the series
     * @param season   the season
     * @return the episodes
     */
    List<Episode> getAllEpisodes(int seriesId, int season);

    /**
     * Get the seasons of a series.
     *
     * @param seriesId the id of the series
     * @return the season
     */
    List<Season> getAllSeasons(int seriesId);

    int insertAllSeasons(List<Season> seasons);

    /**
     * Insert a list of episodes into the database.
     *
     * @param episodes the episodes to insert
     * @return number of rows inserted
     */
    int insertAllEpisodes(List<Episode> episodes);

    /**
     * Delete all episodes of a series from the table.
     *
     * @param seriesId the id of the series
     * @return number of rows deleted
     */
    int deleteAllEpisodes(int seriesId);

    /**
     * Deletes all the episode records from the database.
     * @return number of rows deleted
     */
    int deleteAllEpisodes();

    /**
     * Checks whether an episode is watched or not.
     *
     * @param episodeId the id of the series
     * @return whether the episode is watched or not
     */
    boolean isWatched(int episodeId);

    /**
     * Set the watched property of an episode
     *
     * @param episodeId the id of the series
     * @return number of rows updated
     */
    boolean setWatched(int seriesId, int episodeId, boolean watched);

    /**
     * Get a list of upcoming episodes.
     *
     * @return a list of upcoming episodes
     */
    List<Episode> getUpcomingEpisodes();

    /**
     * Get a list of episodes history.
     *
     * @param includeUnwatched whether to include unwatched episodes
     * @return a list of apisodes
     */
    List<Episode> getEpisodeHistory(boolean includeUnwatched);

    boolean setLiked(int seriesId, int traktId, boolean liked);
}
