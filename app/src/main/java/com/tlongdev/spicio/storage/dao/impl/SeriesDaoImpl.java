package com.tlongdev.spicio.storage.dao.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.tlongdev.spicio.component.StorageComponent;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.DatabaseContract.SeriesEntry;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.util.Utility;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public class SeriesDaoImpl implements SeriesDao {

    @Inject ContentResolver mContentResolver;

    public SeriesDaoImpl(StorageComponent storageComponent) {
        storageComponent.inject(this);
    }

    @Override
    public Series getSeries(int seriesId) {
        return null;
    }

    @Override
    public List<Series> getAllSeries() {
        return null;
    }

    @Override
    public Uri insertSeries(Series series) {
        ContentValues values = new ContentValues();

        values.put(SeriesEntry.COLUMN_TITLE, series.getTitle());
        values.put(SeriesEntry.COLUMN_YEAR, series.getYear());
        values.put(SeriesEntry.COLUMN_TRAKT_ID, series.getTraktId());
        values.put(SeriesEntry.COLUMN_TVDB_ID, series.getTvdbId());
        values.put(SeriesEntry.COLUMN_IMDB_ID, series.getImdbId());
        values.put(SeriesEntry.COLUMN_TMDB_ID, series.getTmdbId());
        values.put(SeriesEntry.COLUMN_TV_RAGE_ID, series.getTvRageId());
        values.put(SeriesEntry.COLUMN_SLUG, series.getSlugName());
        values.put(SeriesEntry.COLUMN_OVERVIEW, series.getOverview());
        values.put(SeriesEntry.COLUMN_FIRST_AIRED, series.getFirstAired().getMillis());
        values.put(SeriesEntry.COLUMN_DAY_OF_AIR, series.getDayOfAiring());
        values.put(SeriesEntry.COLUMN_TIME_OF_AIR, series.getTimeOfAiring().getMillisOfDay());
        values.put(SeriesEntry.COLUMN_AIR_TIME_ZONE, series.getAirTimeZone().getID());
        values.put(SeriesEntry.COLUMN_RUNTIME, series.getRuntime());
        values.put(SeriesEntry.COLUMN_CONTENT_RATING, series.getCertification());
        values.put(SeriesEntry.COLUMN_NETWORK, series.getNetwork());
        values.put(SeriesEntry.COLUMN_TRAILER, series.getTrailer());
        values.put(SeriesEntry.COLUMN_STATUS, series.getStatus());
        values.put(SeriesEntry.COLUMN_TRAKT_RATING, series.getTraktRating());
        values.put(SeriesEntry.COLUMN_TRAKT_RATING_COUNT, series.getTraktRatingCount());
        values.put(SeriesEntry.COLUMN_GENRES, Utility.join(series.getGenres(), "|"));

        // TODO: 2016. 03. 05.
        values.put(SeriesEntry.COLUMN_TVDB_RATING, 0.0);
        values.put(SeriesEntry.COLUMN_TVDB_RATING_COUNT, 0);

        return mContentResolver.insert(SeriesEntry.CONTENT_URI, values);
    }

    @Override
    public void deleteSeries(int seriesId) {

    }

    @Override
    public void deleteAllSeries() {

    }
}
