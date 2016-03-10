package com.tlongdev.spicio.domain.repository;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public interface TraktRepository {

    List<Series> searchSeries(String query);

    Series getSeriesDetails(int traktId);

    Images getImages(int traktId);

    List<Episode> getEpisodesForSeries(int traktId);

    Episode getEpisodeDetails(int traktId, int season, int number);

    List<Season> getSeasons(int traktId);

    List<Episode> getEpisodeImages(int seriesId, int season);
}
