package com.tlongdev.spicio.storage.dao.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.DatabaseContract;
import com.tlongdev.spicio.storage.DatabaseContract.SeriesEntry;
import com.tlongdev.spicio.storage.dao.SeriesDao;
import com.tlongdev.spicio.util.Logger;
import com.tlongdev.spicio.util.Utility;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public class SeriesDaoImpl implements SeriesDao {

    private static final String LOG_TAG = SeriesDaoImpl.class.getSimpleName();

    @Inject ContentResolver mContentResolver;
    @Inject Logger mLogger;

    // TODO: 2016. 03. 08. better projection to improve query performance
    public static final String[] PROJECTION = {
            SeriesEntry._ID,
            SeriesEntry.COLUMN_TITLE,
            SeriesEntry.COLUMN_YEAR,
            SeriesEntry.COLUMN_TRAKT_ID,
            SeriesEntry.COLUMN_TVDB_ID,
            SeriesEntry.COLUMN_IMDB_ID,
            SeriesEntry.COLUMN_TMDB_ID,
            SeriesEntry.COLUMN_TV_RAGE_ID,
            SeriesEntry.COLUMN_SLUG,
            SeriesEntry.COLUMN_OVERVIEW,
            SeriesEntry.COLUMN_FIRST_AIRED,
            SeriesEntry.COLUMN_DAY_OF_AIR,
            SeriesEntry.COLUMN_TIME_OF_AIR,
            SeriesEntry.COLUMN_AIR_TIME_ZONE,
            SeriesEntry.COLUMN_RUNTIME,
            SeriesEntry.COLUMN_CONTENT_RATING,
            SeriesEntry.COLUMN_NETWORK,
            SeriesEntry.COLUMN_TRAILER,
            SeriesEntry.COLUMN_STATUS,
            SeriesEntry.COLUMN_TRAKT_RATING,
            SeriesEntry.COLUMN_TRAKT_RATING_COUNT,
            SeriesEntry.COLUMN_GENRES,
            SeriesEntry.COLUMN_TVDB_RATING,
            SeriesEntry.COLUMN_TVDB_RATING_COUNT,
            SeriesEntry.COLUMN_POSTER_FULL,
            SeriesEntry.COLUMN_POSTER_THUMB,
            SeriesEntry.COLUMN_THUMB,
    };

    public static final int COLUMN_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_YEAR = 2;
    public static final int COLUMN_TRAKT_ID = 3;
    public static final int COLUMN_TVDB_ID = 4;
    public static final int COLUMN_IMDB_ID = 5;
    public static final int COLUMN_TMDB_ID = 6;
    public static final int COLUMN_TV_RAGE_ID = 7;
    public static final int COLUMN_SLUG = 8;
    public static final int COLUMN_OVERVIEW = 9;
    public static final int COLUMN_FIRST_AIRED = 10;
    public static final int COLUMN_DAY_OF_AIR = 11;
    public static final int COLUMN_TIME_OF_AIR = 12;
    public static final int COLUMN_AIR_TIME_ZONE = 13;
    public static final int COLUMN_RUNTIME = 14;
    public static final int COLUMN_CONTENT_RATING = 15;
    public static final int COLUMN_NETWORK = 16;
    public static final int COLUMN_TRAILER = 17;
    public static final int COLUMN_STATUS = 18;
    public static final int COLUMN_TRAKT_RATING = 19;
    public static final int COLUMN_TRAKT_RATING_COUNT = 20;
    public static final int COLUMN_GENRES = 21;
    public static final int COLUMN_TVDB_RATING = 22;
    public static final int COLUMN_TVDB_RATING_COUNT = 23;
    public static final int COLUMN_POSTER_FULL = 24;
    public static final int COLUMN_POSTER_THUMB = 25;
    public static final int COLUMN_THUMB = 26;

    public SeriesDaoImpl(SpicioApplication app) {
        app.getStorageComponent().inject(this);
    }

    @Override
    public Series getSeries(int traktId) {
        mLogger.debug(LOG_TAG, "querying series with id: " + traktId);
        Cursor cursor = mContentResolver.query(
                SeriesEntry.CONTENT_URI,
                PROJECTION,
                SeriesEntry.COLUMN_TRAKT_ID + " = ?",
                new String[]{String.valueOf(traktId)},
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return mapCursorToSeries(cursor);
            } else {
                mLogger.debug(LOG_TAG, "series not found with id: " + traktId);
            }

            cursor.close();
        } else {
            mLogger.warn(LOG_TAG, "query returned null");
        }
        return null;
    }

    @Override
    public List<Series> getAllSeries() {
        mLogger.debug(LOG_TAG, "querying series with id");
        Cursor cursor = mContentResolver.query(
                SeriesEntry.CONTENT_URI,
                PROJECTION,
                null,
                null,
                SeriesEntry.COLUMN_TITLE + " ASC"
        );

        List<Series> seriesList = new LinkedList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    seriesList.add(mapCursorToSeries(cursor));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return seriesList;
    }

    @Override
    public Uri insertSeries(Series series) {
        mLogger.debug(LOG_TAG, "inserting series");

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

        values.put(SeriesEntry.COLUMN_POSTER_FULL, series.getImages().getPoster().getFull());
        values.put(SeriesEntry.COLUMN_POSTER_THUMB, series.getImages().getPoster().getThumb());
        values.put(SeriesEntry.COLUMN_THUMB, series.getImages().getThumb().getFull());

        Uri uri = mContentResolver.insert(SeriesEntry.CONTENT_URI, values);

        if (uri == null) {
            mLogger.warn(LOG_TAG, "insert return null URI");
        } else {
            mLogger.debug(LOG_TAG, "inserted one record into " + SeriesEntry.TABLE_NAME + " table, uri: " + uri.toString());
        }
        return uri;
    }

    @Override
    public void deleteSeries(int seriesId) {

    }

    @Override
    public void deleteAllData() {
        int rowsDeleted = mContentResolver.delete(SeriesEntry.CONTENT_URI, null, null);
        mLogger.debug(LOG_TAG, "deleted " + rowsDeleted + " rows from series table");

        rowsDeleted = mContentResolver.delete(DatabaseContract.EpisodesEntry.CONTENT_URI, null, null);
        mLogger.debug(LOG_TAG, "deleted " + rowsDeleted + " rows from episodes table");

        rowsDeleted = mContentResolver.delete(DatabaseContract.SeasonsEntry.CONTENT_URI, null, null);
        mLogger.debug(LOG_TAG, "deleted " + rowsDeleted + " rows from seasons table");

        rowsDeleted = mContentResolver.delete(DatabaseContract.FeedEntry.CONTENT_URI, null, null);
        mLogger.debug(LOG_TAG, "deleted " + rowsDeleted + " rows from feed table");

        rowsDeleted = mContentResolver.delete(DatabaseContract.FriendsEntry.CONTENT_URI, null, null);
        mLogger.debug(LOG_TAG, "deleted " + rowsDeleted + " rows from friends table");
    }

    @SuppressWarnings("WrongConstant")
    private Series mapCursorToSeries(Cursor cursor) {
        Series series = new Series();

        series.setTitle(cursor.getString(COLUMN_TITLE));
        series.setYear(cursor.getInt(COLUMN_YEAR));
        series.setTraktId(cursor.getInt(COLUMN_TRAKT_ID));
        series.setTvdbId(cursor.getInt(COLUMN_TVDB_ID));
        series.setImdbId(cursor.getString(COLUMN_IMDB_ID));
        series.setTmdbId(cursor.getInt(COLUMN_TMDB_ID));
        series.setTvRageId(cursor.getInt(COLUMN_TV_RAGE_ID));
        series.setSlugName(cursor.getString(COLUMN_SLUG));
        series.setOverview(cursor.getString(COLUMN_OVERVIEW));
        series.setFirstAired(new DateTime(cursor.getLong(COLUMN_FIRST_AIRED)));
        series.setDayOfAiring(cursor.getInt(COLUMN_DAY_OF_AIR));
        series.setTimeOfAiring(LocalTime.fromMillisOfDay(cursor.getLong(COLUMN_TIME_OF_AIR)));
        series.setAirTimeZone(DateTimeZone.forID(cursor.getString(COLUMN_AIR_TIME_ZONE)));
        series.setRuntime(cursor.getInt(COLUMN_RUNTIME));
        series.setCertification(cursor.getString(COLUMN_CONTENT_RATING));
        series.setNetwork(cursor.getString(COLUMN_NETWORK));
        series.setTrailer(cursor.getString(COLUMN_TRAILER));
        series.setStatus(cursor.getInt(COLUMN_STATUS));
        series.setTraktRating(cursor.getDouble(COLUMN_TRAKT_RATING));
        series.setTraktRatingCount(cursor.getInt(COLUMN_TRAKT_RATING_COUNT));
        series.setGenres(cursor.getString(COLUMN_GENRES).split("\\|"));

        // TODO: 2016. 03. 05. tvdb ratings

        Images images = new Images();

        Image poster = new Image();
        poster.setFull(cursor.getString(COLUMN_POSTER_FULL));
        poster.setThumb(cursor.getString(COLUMN_POSTER_THUMB));
        images.setPoster(poster);

        Image thumb = new Image();
        thumb.setFull(cursor.getString(COLUMN_THUMB));
        images.setThumb(thumb);

        series.setImages(images);

        return series;
    }
}
