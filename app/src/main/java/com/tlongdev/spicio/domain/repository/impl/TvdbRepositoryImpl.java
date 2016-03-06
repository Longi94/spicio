package com.tlongdev.spicio.domain.repository.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.TvdbEpisodeOld;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
import com.tlongdev.spicio.network.TvdbInterface;
import com.tlongdev.spicio.network.converter.TvdbModelConverter;
import com.tlongdev.spicio.network.model.TvdbSeriesPayload;
import com.tlongdev.spicio.util.Logger;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbRepositoryImpl implements TvdbRepository {

    private static final String LOG_TAG = TvdbRepositoryImpl.class.getSimpleName();

    @Inject @Named("tvdb") Retrofit retrofit;
    @Inject Logger logger;

    private TvdbInterface tvdbInterface;

    public TvdbRepositoryImpl(SpicioApplication app) {
        app.getNetworkComponent().inject(this);
        tvdbInterface = retrofit.create(TvdbInterface.class);
    }

    @Override
    public List<TvdbSeriesOld> searchSeries(String query) {
        try {
            Call<TvdbSeriesPayload> call = tvdbInterface.getSeries(query);

            logger.verbose(LOG_TAG, "calling " + call.request().url());
            Response<TvdbSeriesPayload> response = call.execute();

            if (response.body() == null) {
                int code = response.raw().code();
                logger.error(LOG_TAG, "call returned null with code " + code);
            } else {
                logger.verbose(LOG_TAG, "converting tvdbseries object");
                return TvdbModelConverter.convertToDomainModel(response.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TvdbSeriesOld getSeries(int id) {
        // TODO: 2016. 02. 28.  
        return null;
    }

    @Override
    public TvdbEpisodeOld getEpisode(int seriesId, int season, int episodeNumber) {
        // TODO: 2016. 02. 28.  
        return null;
    }

    @Override
    public TvdbEpisodeOld getEpisode(int seriesId, int absoluteNumber) {
        // TODO: 2016. 02. 28.  
        return null;
    }
}
