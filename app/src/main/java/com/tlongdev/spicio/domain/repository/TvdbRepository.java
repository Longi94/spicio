package com.tlongdev.spicio.domain.repository;

import com.tlongdev.spicio.domain.model.TvdbEpisodeOld;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;

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
    List<TvdbSeriesOld> searchSeries(String query);

    /**
     * Finds a series by their ID.
     *
     * @param id the ID of the series
     * @return the series
     */
    TvdbSeriesOld getSeries(int id);

    /**
     * Gets an episode.
     *
     * @param seriesId      the ID of the series
     * @param season        the season of the series the episode is in
     * @param episodeNumber the number of the episode in the season
     * @return the episode
     */
    TvdbEpisodeOld getEpisode(int seriesId, int season, int episodeNumber);

    /**
     * Gets an episode.
     *
     * @param seriesId       the ID of the series
     * @param absoluteNumber the absolute number of the episode
     * @return the episode
     */
    TvdbEpisodeOld getEpisode(int seriesId, int absoluteNumber);
}
