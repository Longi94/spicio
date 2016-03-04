package com.tlongdev.spicio.domain.repository;

import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.domain.model.TvdbEpisodeOld;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;
import com.tlongdev.spicio.network.TvdbInterface;
import com.tlongdev.spicio.network.converter.TvdbModelConverter;
import com.tlongdev.spicio.network.model.TvdbSeriesPayload;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;

/**
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbRepositoryImpl implements TvdbRepository {

    @Inject @Named("tvdb") Retrofit retrofit;

    private TvdbInterface tvdbInterface;

    public TvdbRepositoryImpl(NetworkComponent networkComponent) {
        networkComponent.inject(this);
        tvdbInterface = retrofit.create(TvdbInterface.class);
    }

    @Override
    public List<TvdbSeriesOld> searchSeries(String query) {
        try {
            TvdbSeriesPayload payload = tvdbInterface.getSeries(query).execute().body();
            return TvdbModelConverter.convertToDomainModel(payload);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
