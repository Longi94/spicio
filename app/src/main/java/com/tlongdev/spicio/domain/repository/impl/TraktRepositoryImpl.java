package com.tlongdev.spicio.domain.repository.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.network.TraktApiInterface;
import com.tlongdev.spicio.network.converter.TraktModelConverter;
import com.tlongdev.spicio.network.model.trakt.TraktEpisode;
import com.tlongdev.spicio.network.model.trakt.TraktSearchResult;
import com.tlongdev.spicio.network.model.trakt.TraktSeason;
import com.tlongdev.spicio.network.model.trakt.TraktSeries;
import com.tlongdev.spicio.util.Logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class TraktRepositoryImpl implements TraktRepository {

    private static final String LOG_TAG = TraktRepositoryImpl.class.getSimpleName();

    @Inject TraktApiInterface mInterface;
    @Inject Logger logger;

    public TraktRepositoryImpl(SpicioApplication app) {
        app.getNetworkComponent().inject(this);
    }

    @Override
    public List<Series> searchSeries(String query) {
        try {

            Call<List<TraktSearchResult>> call = mInterface.searchByText(query, "show");

            logger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<TraktSearchResult>> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                List<Series> series = new LinkedList<>();

                logger.debug(LOG_TAG, "converting search result result");

                for (TraktSearchResult result : response.body()) {
                    series.add(TraktModelConverter.convertToSeries(result.getSeries()));
                }

                logger.debug(LOG_TAG, "search API returned " + series.size() + " items");

                return series;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Series getSeriesDetails(int traktId) {
        try {
            Call<TraktSeries> call = mInterface.getSeriesDetails(String.valueOf(traktId));

            logger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<TraktSeries> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                logger.debug(LOG_TAG, "converting traktseries object");
                return TraktModelConverter.convertToSeries(response.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Images getImages(int traktId) {
        try {

            Call<TraktSeries> call = mInterface.getSeriesImages(String.valueOf(traktId));

            logger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<TraktSeries> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                logger.debug(LOG_TAG, "converting traktseries object");
                return TraktModelConverter.convertToImages(response.body().getImages());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Episode> getEpisodesForSeries(int traktId) {
        try {
            Call<List<TraktSeason>> call = mInterface.getSeasonsEpisodes(String.valueOf(traktId));

            logger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<TraktSeason>> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                logger.debug(LOG_TAG, "converting trakt season objects");
                List<Episode> episodes = new LinkedList<>();

                for (TraktSeason traktSeason : response.body()) {
                    for (TraktEpisode traktEpisode : traktSeason.getEpisodes()) {
                        Episode episode = TraktModelConverter.convertToEpisode(traktEpisode);
                        episode.setSeriesId(traktId);
                        episodes.add(episode);
                    }
                }

                logger.debug(LOG_TAG, "episodes API returned " + episodes.size() + " episodes");

                return episodes;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Episode getEpisodeDetails(int traktId, int season, int number) {
        return null;
    }

    @Override
    public List<Season> getSeasons(int traktId) {

        try {
            Call<List<TraktSeason>> call = mInterface.getSeasonsImages(String.valueOf(traktId));

            logger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<TraktSeason>> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                logger.debug(LOG_TAG, "converting trakt season objects");
                List<Season> seasons = new LinkedList<>();

                for (TraktSeason traktSeason : response.body()) {
                    Season season = TraktModelConverter.convertToSeason(traktId, traktSeason);
                    season.setSeriesId(traktId);
                    seasons.add(season);
                }

                logger.debug(LOG_TAG, "seasons API returned " + seasons.size() + " seasons");

                return seasons;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Episode> getEpisodeImages(int seriesId, int season) {

        try {
            Call<List<TraktEpisode>> call = mInterface.getSeasonEpisodesImages(String.valueOf(seriesId), season);

            logger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<TraktEpisode>> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                logger.debug(LOG_TAG, "converting trakt episode objects");
                List<Episode> episodes = new LinkedList<>();

                for (TraktEpisode traktEpisode : response.body()) {
                    Episode episode = TraktModelConverter.convertToEpisode(traktEpisode);
                    episode.setSeriesId(seriesId);
                    episodes.add(episode);
                }

                logger.debug(LOG_TAG, "seasons API returned " + episodes.size() + " episodes");

                return episodes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Episode> getSeasonEpisodes(int seriesId, int season) {

        try {
            Call<List<TraktEpisode>> call = mInterface.getSeasonEpisodes(String.valueOf(seriesId), season);

            logger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<TraktEpisode>> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                logger.debug(LOG_TAG, "converting trakt episode objects");
                List<Episode> episodes = new LinkedList<>();

                for (TraktEpisode traktEpisode : response.body()) {
                    Episode episode = TraktModelConverter.convertToEpisode(traktEpisode);
                    episode.setSeriesId(seriesId);
                    episodes.add(episode);
                }

                logger.debug(LOG_TAG, "seasons API returned " + episodes.size() + " episodes");

                return episodes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
