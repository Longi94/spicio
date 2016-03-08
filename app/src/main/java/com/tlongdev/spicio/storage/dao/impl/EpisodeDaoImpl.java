package com.tlongdev.spicio.storage.dao.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.storage.DatabaseContract.SeriesEntry;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.util.Logger;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
public class EpisodeDaoImpl implements EpisodeDao {

    private static final String LOG_TAG = EpisodeDaoImpl.class.getSimpleName();

    @Inject ContentResolver mContentResolver;
    @Inject Logger logger;

    // TODO: 2016. 03. 08. better projection to improve query performance
    public static final String[] PROJECTION = new String[]{
            SeriesEntry._ID,
            COLUMN_SERIES_ID,
            COLUMN_SEASON,
            COLUMN_EPISODE_NUMBER,
            COLUMN_TITLE,
            COLUMN_TRAKT_ID,
            COLUMN_TVDB_ID,
            COLUMN_IMDB_ID,
            COLUMN_TMDB_ID,
            COLUMN_TV_RAGE_ID,
            COLUMN_SLUG,
            COLUMN_ABSOLUTE_NUMBER,
            COLUMN_OVERVIEW,
            COLUMN_TRAKT_RATING,
            COLUMN_TRAKT_RATING_COUNT,
            COLUMN_FIRST_AIRED,
            COLUMN_SCREENSHOT_FULL,
            COLUMN_SCREENSHOT_THUMB,
            COLUMN_WATCHED,
            COLUMN_LIKED,
            COLUMN_SKIPPED
    };

    public EpisodeDaoImpl(SpicioApplication application) {
        application.getStorageComponent().inject(this);
    }

    @Override
    public Episode getEpisode(int traktId) {
        logger.debug(LOG_TAG, "querying episode with id: " + traktId);
        Cursor cursor = mContentResolver.query(
                EpisodesEntry.CONTENT_URI,
                PROJECTION,
                COLUMN_TRAKT_ID + " = ?",
                new String[]{String.valueOf(traktId)},
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return mapCursorToEpisode(cursor);
            } else {
                logger.debug(LOG_TAG, "episode not found with id: " + traktId);
            }
            cursor.close();
        } else {
            logger.warn(LOG_TAG, "query returned null");
        }

        return null;
    }

    @Override
    public List<Episode> getAllEpisodes() {
        logger.debug(LOG_TAG, "querying all episodes");
        Cursor cursor = mContentResolver.query(
                EpisodesEntry.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null
        );

        List<Episode> episodes = new LinkedList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    episodes.add(mapCursorToEpisode(cursor));
                } while (cursor.moveToNext());

            } else {
                logger.debug(LOG_TAG, "cursor is empty");
            }
            cursor.close();
        } else {
            logger.warn(LOG_TAG, "query returned null");
        }
        return episodes;
    }

    @Override
    public List<Episode> getAllEpisodes(int seriesId) {
        logger.debug(LOG_TAG, "querying all episodes with series id: " + seriesId);
        Cursor cursor = mContentResolver.query(
                EpisodesEntry.CONTENT_URI,
                PROJECTION,
                COLUMN_SERIES_ID + " = ?",
                new String[]{String.valueOf(seriesId)},
                null
        );

        List<Episode> episodes = new LinkedList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    episodes.add(mapCursorToEpisode(cursor));
                } while (cursor.moveToNext());

            } else {
                logger.debug(LOG_TAG, "episodes not found with series id: " + seriesId);
            }
            cursor.close();
        } else {
            logger.warn(LOG_TAG, "query returned null");
        }
        return episodes;
    }

    @Override
    public List<Episode> getAllEpisodes(int seriesId, int season) {
        logger.debug(LOG_TAG, "querying all episodes with series id: " + seriesId + "; season: " + season);
        Cursor cursor = mContentResolver.query(
                EpisodesEntry.CONTENT_URI,
                PROJECTION,
                COLUMN_SERIES_ID + " = ? AND " + COLUMN_SEASON + " = ?",
                new String[]{String.valueOf(seriesId), String.valueOf(season)},
                null
        );

        List<Episode> episodes = new LinkedList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    episodes.add(mapCursorToEpisode(cursor));
                } while (cursor.moveToNext());

            } else {
                logger.debug(LOG_TAG, "episodes not found with series id: " + seriesId+ "; season: " + season);
            }
            cursor.close();
        } else {
            logger.warn(LOG_TAG, "query returned null");
        }
        return episodes;
    }

    @Override
    public List<Season> getAllSeasons(int seriesId) {
        return null;
    }

    @Override
    public int insertAllEpisodes(List<Episode> episodes) {

        logger.debug(LOG_TAG, "inserting episodes");

        Vector<ContentValues> cVVector = new Vector<>();

        for (Episode episode : episodes) {
            ContentValues values = new ContentValues();
            
            values.put(COLUMN_SERIES_ID, episode.getSeriesId());
            values.put(COLUMN_SEASON, episode.getSeason());
            values.put(COLUMN_EPISODE_NUMBER, episode.getNumber());
            values.put(COLUMN_TITLE, episode.getTitle());
            values.put(COLUMN_TRAKT_ID, episode.getTraktId());
            values.put(COLUMN_TVDB_ID, episode.getTvdbId());
            values.put(COLUMN_IMDB_ID, episode.getImdbId());
            values.put(COLUMN_TMDB_ID, episode.getTmdbId());
            values.put(COLUMN_TV_RAGE_ID, episode.getTvRageId());
            values.put(COLUMN_SLUG, episode.getSlugName());
            values.put(COLUMN_ABSOLUTE_NUMBER, episode.getAbsoluteNumber());
            values.put(COLUMN_OVERVIEW, episode.getOverview());
            values.put(COLUMN_TRAKT_RATING, episode.getTraktRating());
            values.put(COLUMN_TRAKT_RATING_COUNT, episode.getTraktRatingCount());

            if (episode.getFirstAired() != null) {
                values.put(COLUMN_FIRST_AIRED, episode.getFirstAired().getMillis());
            }

            if (episode.getImages() != null && episode.getImages().getScreenshot() != null) {
                values.put(COLUMN_SCREENSHOT_FULL, episode.getImages().getScreenshot().getFull());
                values.put(COLUMN_SCREENSHOT_THUMB, episode.getImages().getScreenshot().getThumb());
            }

            cVVector.add(values);
        }

        int rowsInserted = 0;

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            //Insert all the data into the database
            rowsInserted = mContentResolver.bulkInsert(EpisodesEntry.CONTENT_URI, cvArray);
        }

        Log.v(LOG_TAG, "inserted " + rowsInserted + " rows into episodes table");
        return rowsInserted;
    }

    @Override
    public int deleteAllEpisodes(int seriesId) {
        return 0;
    }

    @Override
    public int deleteAllEpisodes() {
        return 0;
    }

    @Override
    public boolean isWatched(int episodeId) {
        return false;
    }

    @Override
    public int setWatched(int episodeId, boolean watched) {
        return 0;
    }

    @Override
    public List<Episode> getUpcomingEpisodes() {
        return null;
    }

    @Override
    public List<Episode> getEpisodeHistory(boolean includeUnwatched) {
        return null;
    }

    @Override
    public int setLiked(int traktId, boolean liked) {
        return 0;
    }

    @Override
    public int setSkipped(int traktId, boolean skipped) {
        return 0;
    }

    private Episode mapCursorToEpisode(Cursor cursor) {
        Episode episode = new Episode();

        episode.setImages(new Images());
        episode.getImages().setScreenshot(new Image());
        episode.getImages().setThumb(new Image());

        int column;

        column = cursor.getColumnIndex(COLUMN_SERIES_ID);
        if (column != -1) {
            episode.setSeriesId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_SEASON);
        if (column != -1) {
            episode.setSeason(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_EPISODE_NUMBER);
        if (column != -1) {
            episode.setNumber(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_TITLE);
        if (column != -1) {
            episode.setTitle(cursor.getString(column));
        }

        column = cursor.getColumnIndex(COLUMN_TRAKT_ID);
        if (column != -1) {
            episode.setTraktId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_TVDB_ID);
        if (column != -1) {
            episode.setTvdbId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_IMDB_ID);
        if (column != -1) {
            episode.setImdbId(cursor.getString(column));
        }

        column = cursor.getColumnIndex(COLUMN_TMDB_ID);
        if (column != -1) {
            episode.setTmdbId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_TV_RAGE_ID);
        if (column != -1) {
            episode.setTvRageId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_SLUG);
        if (column != -1) {
            episode.setSlugName(cursor.getString(column));
        }

        column = cursor.getColumnIndex(COLUMN_ABSOLUTE_NUMBER);
        if (column != -1) {
            episode.setAbsoluteNumber(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_OVERVIEW);
        if (column != -1) {
            episode.setOverview(cursor.getString(column));
        }

        column = cursor.getColumnIndex(COLUMN_TRAKT_RATING);
        if (column != -1) {
            episode.setTraktRating(cursor.getDouble(column));
        }

        column = cursor.getColumnIndex(COLUMN_TRAKT_RATING_COUNT);
        if (column != -1) {
            episode.setTraktRatingCount(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_FIRST_AIRED);
        if (column != -1) {
            episode.setFirstAired(new DateTime(cursor.getLong(column)));
        }

        column = cursor.getColumnIndex(COLUMN_SCREENSHOT_FULL);
        if (column != -1) {
            episode.getImages().getScreenshot().setFull(cursor.getString(column));
        }

        column = cursor.getColumnIndex(COLUMN_SCREENSHOT_THUMB);
        if (column != -1) {
            episode.getImages().getScreenshot().setThumb(cursor.getString(column));
        }

        column = cursor.getColumnIndex(COLUMN_WATCHED);
        if (column != -1) {
            episode.setWatched(cursor.getInt(column) == 1);
        }

        column = cursor.getColumnIndex(COLUMN_LIKED);
        if (column != -1) {
            episode.setLiked(cursor.getInt(column) == 1);
        }

        column = cursor.getColumnIndex(COLUMN_SKIPPED);
        if (column != -1) {
            episode.setSkipped(cursor.getInt(column) == 1);
        }

        return episode;
    }
}
