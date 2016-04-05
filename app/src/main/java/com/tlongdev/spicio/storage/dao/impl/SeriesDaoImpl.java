package com.tlongdev.spicio.storage.dao.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.storage.DatabaseContract.ActivityEntry;
import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FeedEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FriendsEntry;
import com.tlongdev.spicio.storage.DatabaseContract.SeasonsEntry;
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

    public SeriesDaoImpl(SpicioApplication app) {
        app.getStorageComponent().inject(this);
    }

    @Override
    public Series getSeries(int traktId) {
        mLogger.debug(LOG_TAG, "querying series with id: " + traktId);
        Cursor cursor = mContentResolver.query(
                SeriesEntry.CONTENT_URI,
                new String[]{
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
                },
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
                new String[]{
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
                },
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
        mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from series table");

        rowsDeleted = mContentResolver.delete(EpisodesEntry.CONTENT_URI, null, null);
        mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from episodes table");

        rowsDeleted = mContentResolver.delete(SeasonsEntry.CONTENT_URI, null, null);
        mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from seasons table");

        rowsDeleted = mContentResolver.delete(FeedEntry.CONTENT_URI, null, null);
        mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from feed table");

        rowsDeleted = mContentResolver.delete(FriendsEntry.CONTENT_URI, null, null);
        mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from friends table");

        rowsDeleted = mContentResolver.delete(ActivityEntry.CONTENT_URI, null, null);
        mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from activity table");
    }

    @SuppressWarnings("WrongConstant")
    private Series mapCursorToSeries(Cursor cursor) {
        Series series = new Series();

        int column;

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TITLE);
        if (column != -1) {
            series.setTitle(cursor.getString(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_YEAR);
        if (column != -1) {
            series.setYear(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TRAKT_ID);
        if (column != -1) {
            series.setTraktId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TVDB_ID);
        if (column != -1) {
            series.setTvdbId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_IMDB_ID);
        if (column != -1) {
            series.setImdbId(cursor.getString(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TMDB_ID);
        if (column != -1) {
            series.setTmdbId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TV_RAGE_ID);
        if (column != -1) {
            series.setTvRageId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_SLUG);
        if (column != -1) {
            series.setSlugName(cursor.getString(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_OVERVIEW);
        if (column != -1) {
            series.setOverview(cursor.getString(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_FIRST_AIRED);
        if (column != -1) {
            series.setFirstAired(new DateTime(cursor.getLong(column)));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_DAY_OF_AIR);
        if (column != -1) {
            series.setDayOfAiring(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TIME_OF_AIR);
        if (column != -1) {
            series.setTimeOfAiring(LocalTime.fromMillisOfDay(cursor.getLong(column)));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_AIR_TIME_ZONE);
        if (column != -1) {
            series.setAirTimeZone(DateTimeZone.forID(cursor.getString(column)));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_RUNTIME);
        if (column != -1) {
            series.setRuntime(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_CONTENT_RATING);
        if (column != -1) {
            series.setCertification(cursor.getString(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_NETWORK);
        if (column != -1) {
            series.setNetwork(cursor.getString(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TRAILER);
        if (column != -1) {
            series.setTrailer(cursor.getString(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_STATUS);
        if (column != -1) {
            series.setStatus(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TRAKT_RATING);
        if (column != -1) {
            series.setTraktRating(cursor.getDouble(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_TRAKT_RATING_COUNT);
        if (column != -1) {
            series.setTraktRatingCount(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_GENRES);
        if (column != -1) {
            series.setGenres(cursor.getString(column).split("\\|"));
        }

        // TODO: 2016. 03. 05. tvdb ratings

        Images images = new Images();
        Image poster = new Image();

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_POSTER_FULL);
        if (column != -1) {
            poster.setFull(cursor.getString(column));
        }

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_POSTER_THUMB);
        if (column != -1) {
            poster.setThumb(cursor.getString(column));
        }

        images.setPoster(poster);

        Image thumb = new Image();

        column = cursor.getColumnIndex(SeriesEntry.COLUMN_THUMB);
        if (column != -1) {
            thumb.setFull(cursor.getString(column));
        }

        images.setThumb(thumb);
        series.setImages(images);

        return series;
    }
}
