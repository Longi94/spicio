package com.tlongdev.spicio.repository;

import com.tlongdev.spicio.model.Episode;
import com.tlongdev.spicio.model.Series;

import java.util.List;

/**
 * Inner Layer, Repository.
 *
 * @author Long
 * @since 2016. 02. 28.
 */
public interface TvdbRepository {

    /**
     * Search for series.
     *
     * @param query the query to search for
     * @return series matching the query
     */
    List<Series> searchSeries(String query);

    /**
     * Finds a series by their ID.
     *
     * @param id the ID of the series
     * @return the series
     */
    Series getSeries(int id);

    /**
     * Gets an episode.
     *
     * @param seriesId      the ID of the series
     * @param season        the season of the series the episode is in
     * @param episodeNumber the number of the episode in the season
     * @return the episode
     */
    Episode getEpisode(int seriesId, int season, int episodeNumber);

    /**
     * Gets an episode.
     *
     * @param seriesId       the ID of the series
     * @param absoluteNumber the absolute number of the episode
     * @return the episode
     */
    Episode getEpisode(int seriesId, int absoluteNumber);
}
