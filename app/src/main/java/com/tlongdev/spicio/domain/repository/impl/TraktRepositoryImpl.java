package com.tlongdev.spicio.domain.repository.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.network.TraktApiInterface;
import com.tlongdev.spicio.network.converter.TraktModelConverter;
import com.tlongdev.spicio.network.model.TraktEpisode;
import com.tlongdev.spicio.network.model.TraktSearchResult;
import com.tlongdev.spicio.network.model.TraktSeason;
import com.tlongdev.spicio.network.model.TraktSeries;
import com.tlongdev.spicio.util.Logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class TraktRepositoryImpl implements TraktRepository {

    private static final String LOG_TAG = TraktRepositoryImpl.class.getSimpleName();

    @Inject @Named("trakt") Retrofit retrofit;
    @Inject Logger logger;

    TraktApiInterface traktInterface;

    public TraktRepositoryImpl(SpicioApplication app) {
        app.getNetworkComponent().inject(this);
        traktInterface = retrofit.create(TraktApiInterface.class);
    }

    @Override
    public List<Series> searchSeries(String query) {
        try {

            Call<List<TraktSearchResult>> call = traktInterface.searchByText(query, "show");

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
            Call<TraktSeries> call = traktInterface.getSeriesDetails(String.valueOf(traktId));

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
    public Images getImages(int traktId, boolean isSeries) {

        if (isSeries) {
            try {

                Call<TraktSeries> call = traktInterface.getSeriesImages(String.valueOf(traktId));

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
        } else {
            // TODO: 2016. 03. 08.
        }

        return null;
    }

    @Override
    public List<Episode> getEpisodesForSeries(int traktId) {
        try {
            Call<List<TraktSeason>> call = traktInterface.getSeasonsEpisodes(String.valueOf(traktId));

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
                        episodes.add(TraktModelConverter.convertToEpisode(traktEpisode));
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
            Call<List<TraktSeason>> call = traktInterface.getSeasonsImages(String.valueOf(traktId));

            logger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<TraktSeason>> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                logger.debug(LOG_TAG, "converting trakt season objects");
                List<Season> seasons = new LinkedList<>();

                for (TraktSeason traktSeason : response.body()) {
                    seasons.add(TraktModelConverter.convertToSeason(traktId, traktSeason));
                }

                logger.debug(LOG_TAG, "seasons API returned " + seasons.size() + " seasons");

                return seasons;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
