package com.tlongdev.spicio.domain.repository;

import android.content.Context;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.network.TvdbInterface;
import com.tlongdev.spicio.network.converter.TvdbModelConverter;
import com.tlongdev.spicio.network.model.SeriesPayload;
import com.tlongdev.spicio.presentation.ui.activity.MainActivity;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbServiceRepository implements TvdbRepository {

    @Inject TvdbInterface tvdbInterface;

    public TvdbServiceRepository(Context context) {
        ((SpicioApplication)((MainActivity)context).getApplication())
                .getNetWorkComponent().inject(this);
    }

    @Override
    public List<Series> searchSeries(String query) {
        try {
            SeriesPayload payload = tvdbInterface.getSeries(query).execute().body();
            return TvdbModelConverter.convertToDomainModel(payload);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Series getSeries(int id) {
        // TODO: 2016. 02. 28.  
        return null;
    }

    @Override
    public Episode getEpisode(int seriesId, int season, int episodeNumber) {
        // TODO: 2016. 02. 28.  
        return null;
    }

    @Override
    public Episode getEpisode(int seriesId, int absoluteNumber) {
        // TODO: 2016. 02. 28.  
        return null;
    }
}
