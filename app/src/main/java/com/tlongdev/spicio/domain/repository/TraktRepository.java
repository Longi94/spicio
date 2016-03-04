package com.tlongdev.spicio.domain.repository;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public interface TraktRepository {

    List<Series> searchSeries(String query);

    Series getSeriesDetails(int traktId);

    List<Episode> getEpisodesForSeries(int traktId);

    Episode getEpisodeDetails(int traktId, int season, int number);
}
