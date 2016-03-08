package com.tlongdev.spicio.storage.dao;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;

import java.util.List;

/**
 * Middle Layer, Converter.
 *
 * @author Long
 * @since 2016. 02. 29.
 */
public interface EpisodeDao {
    
    String COLUMN_SERIES_ID = EpisodesEntry.COLUMN_SERIES_ID;
    String COLUMN_SEASON = EpisodesEntry.COLUMN_SEASON;
    String COLUMN_EPISODE_NUMBER = EpisodesEntry.COLUMN_EPISODE_NUMBER;
    String COLUMN_TITLE = EpisodesEntry.COLUMN_TITLE;
    String COLUMN_TRAKT_ID = EpisodesEntry.COLUMN_TRAKT_ID;
    String COLUMN_TVDB_ID = EpisodesEntry.COLUMN_TVDB_ID;
    String COLUMN_IMDB_ID = EpisodesEntry.COLUMN_IMDB_ID;
    String COLUMN_TMDB_ID = EpisodesEntry.COLUMN_TMDB_ID;
    String COLUMN_TV_RAGE_ID = EpisodesEntry.COLUMN_TV_RAGE_ID;
    String COLUMN_SLUG = EpisodesEntry.COLUMN_SLUG;
    String COLUMN_ABSOLUTE_NUMBER = EpisodesEntry.COLUMN_ABSOLUTE_NUMBER;
    String COLUMN_OVERVIEW = EpisodesEntry.COLUMN_OVERVIEW;
    String COLUMN_TRAKT_RATING = EpisodesEntry.COLUMN_TRAKT_RATING;
    String COLUMN_TRAKT_RATING_COUNT = EpisodesEntry.COLUMN_TRAKT_RATING_COUNT;
    String COLUMN_FIRST_AIRED = EpisodesEntry.COLUMN_FIRST_AIRED;
    String COLUMN_SCREENSHOT_FULL = EpisodesEntry.COLUMN_SCREENSHOT_FULL;
    String COLUMN_SCREENSHOT_THUMB = EpisodesEntry.COLUMN_SCREENSHOT_THUMB;
    String COLUMN_WATCHED = EpisodesEntry.COLUMN_WATCHED;
    String COLUMN_LIKED = EpisodesEntry.COLUMN_LIKED;
    String COLUMN_SKIPPED = EpisodesEntry.COLUMN_SKIPPED;

    String COLUMN_TVDB_RATING = EpisodesEntry.COLUMN_TVDB_RATING;
    
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
     * Get all the episodes of a series.
     *
     * @param seriesId the id of the series
     * @return episodes of a series
     */
    List<Episode> getAllEpisodes(int seriesId);

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
     * @param watched   watched or not
     * @return number of rows updated
     */
    int setWatched(int episodeId, boolean watched);

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

    int setLiked(int traktId, boolean liked);

    int setSkipped(int traktId, boolean skipped);
}
