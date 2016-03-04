package com.tlongdev.spicio.domain.repository;

import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.network.TraktApiInterface;
import com.tlongdev.spicio.network.converter.TraktModelConverter;
import com.tlongdev.spicio.network.model.TraktSearchResult;
import com.tlongdev.spicio.network.model.TraktSeries;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class TraktRepositoryImpl implements TraktRepository {

    @Inject @Named("trakt") Retrofit retrofit;

    TraktApiInterface traktInterface;

    public TraktRepositoryImpl(NetworkComponent networkComponent) {
        networkComponent.inject(this);
        traktInterface = retrofit.create(TraktApiInterface.class);
    }

    @Override
    public List<Series> searchSeries(String query) {
        try {

            List<TraktSearchResult> results = traktInterface.searchByText(query, "show").execute().body();
            List<Series> series = new LinkedList<>();

            for (TraktSearchResult result : results) {
                series.add(TraktModelConverter.convertToSeries(result.getSeries()));
            }

            return series;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Series getSeriesDetails(int traktId) {
        try {
            Response<TraktSeries> response = traktInterface.getSeriesDetails(String.valueOf(traktId)).execute();

            return TraktModelConverter.convertToSeries(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Episode> getEpisodesForSeries(int traktId) {
        return null;
    }

    @Override
    public Episode getEpisodeDetails(int traktId, int season, int number) {
        return null;
    }
}
