package com.tlongdev.spicio.storage.dao.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.ActivityType;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.storage.DatabaseContract.ActivityEntry;
import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.storage.DatabaseContract.SeasonsEntry;
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
    @Inject SQLiteOpenHelper mOpenHelper;
    @Inject Logger mLogger;

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
            COLUMN_LIKED
    };

    public EpisodeDaoImpl(SpicioApplication application) {
        application.getStorageComponent().inject(this);
    }

    @Override
    public Episode getEpisode(int traktId) {
        mLogger.debug(LOG_TAG, "querying episode with id: " + traktId);
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
                mLogger.debug(LOG_TAG, "episode not found with id: " + traktId);
            }
            cursor.close();
        } else {
            mLogger.warn(LOG_TAG, "query returned null");
        }

        return null;
    }

    @Override
    public Episode getEpisode(int seriesId, int season, int episode) {
        mLogger.debug(LOG_TAG, "querying episode with seriesid: " + seriesId + ", season: " + season + ", episode: " + episode);
        Cursor cursor = mContentResolver.query(
                EpisodesEntry.CONTENT_URI,
                PROJECTION,
                COLUMN_SERIES_ID + " = ? AND " + COLUMN_SEASON + " = ? AND " + COLUMN_EPISODE_NUMBER + " = ?",
                new String[]{String.valueOf(seriesId), String.valueOf(season), String.valueOf(episode)},
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return mapCursorToEpisode(cursor);
            } else {
                mLogger.debug(LOG_TAG, "episode not found with seriesid: " + seriesId + ", season: " + season + ", episode: " + episode);
            }
            cursor.close();
        } else {
            mLogger.warn(LOG_TAG, "query returned null");
        }

        return null;
    }

    @Override
    public List<Episode> getAllEpisodes() {
        mLogger.debug(LOG_TAG, "querying all episodes");
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
                mLogger.debug(LOG_TAG, "cursor is empty");
            }
            cursor.close();
        } else {
            mLogger.warn(LOG_TAG, "query returned null");
        }
        return episodes;
    }

    @Override
    public List<Episode> getAllEpisodes(int seriesId) {
        mLogger.debug(LOG_TAG, "querying all episodes with series id: " + seriesId);
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
                mLogger.debug(LOG_TAG, "episodes not found with series id: " + seriesId);
            }
            cursor.close();
        } else {
            mLogger.warn(LOG_TAG, "query returned null");
        }
        return episodes;
    }

    @Override
    public List<Episode> getAllEpisodes(int seriesId, int season) {
        mLogger.debug(LOG_TAG, "querying all episodes with series id: " + seriesId + "; season: " + season);
        Cursor cursor = mContentResolver.query(
                EpisodesEntry.CONTENT_URI,
                PROJECTION,
                COLUMN_SERIES_ID + " = ? AND " + COLUMN_SEASON + " = ?",
                new String[]{String.valueOf(seriesId), String.valueOf(season)},
                COLUMN_EPISODE_NUMBER + " ASC"
        );

        List<Episode> episodes = new LinkedList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    episodes.add(mapCursorToEpisode(cursor));
                } while (cursor.moveToNext());

            } else {
                mLogger.debug(LOG_TAG, "episodes not found with series id: " + seriesId + "; season: " + season);
            }
            cursor.close();
        } else {
            mLogger.warn(LOG_TAG, "query returned null");
        }
        return episodes;
    }

    @Override
    public List<Season> getAllSeasons(int traktId) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        mLogger.debug(LOG_TAG, "querying seasons for series(" + traktId + ")");

        //Query the seasons with the number of watched and skipper episodes
        String query = "SELECT " + SeasonsEntry.TABLE_NAME + "." + COLUMN_NUMBER + ", " +
                SeasonsEntry.TABLE_NAME + "." + COLUMN_POSTER_FULL + ", " +
                SeasonsEntry.TABLE_NAME + "." + COLUMN_POSTER_THUMB + ", " +
                SeasonsEntry.TABLE_NAME + "." + COLUMN_THUMB + ", " +
                "watches." + COLUMN_WATCH_COUNT + ", " +
                "skips." + COLUMN_SKIP_COUNT +
                " FROM " + SeasonsEntry.TABLE_NAME +
                " LEFT JOIN (SELECT " + EpisodesEntry.TABLE_NAME + "." + COLUMN_SERIES_ID + ", count(*) AS " + COLUMN_WATCH_COUNT +
                "    FROM " + EpisodesEntry.TABLE_NAME +
                "    WHERE " + EpisodesEntry.TABLE_NAME + "." + COLUMN_SERIES_ID + " = ? AND " + EpisodesEntry.TABLE_NAME + "." + COLUMN_WATCHED + " = 1) AS watches " +
                "ON " + SeasonsEntry.TABLE_NAME + "." + COLUMN_SERIES_ID + " = watches." + COLUMN_SERIES_ID +
                " LEFT JOIN (SELECT " + EpisodesEntry.TABLE_NAME + "." + COLUMN_SERIES_ID + ", count(*) AS " + COLUMN_SKIP_COUNT +
                "    FROM " + EpisodesEntry.TABLE_NAME +
                "    WHERE " + EpisodesEntry.TABLE_NAME + "." + COLUMN_SERIES_ID + " = ? AND " + EpisodesEntry.TABLE_NAME + "." + COLUMN_WATCHED + " = 2) AS skips " +
                "ON " + SeasonsEntry.TABLE_NAME + "." + COLUMN_SERIES_ID + " = skips." + COLUMN_SERIES_ID +
                " WHERE " + SeasonsEntry.TABLE_NAME + "." + COLUMN_SERIES_ID + " = ? " +
                "ORDER BY " + SeasonsEntry.TABLE_NAME + "." + COLUMN_NUMBER + " DESC";

        mLogger.debug(LOG_TAG, "raw query: " + query);

        Cursor cursor = db.rawQuery(query,
                new String[]{String.valueOf(traktId), String.valueOf(traktId), String.valueOf(traktId)}
        );

        List<Season> seasons = new LinkedList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Season season = mapCursorToSeason(cursor);
                    season.setSeriesId(traktId);
                    seasons.add(season);
                } while (cursor.moveToNext());
            } else {
                mLogger.debug(LOG_TAG, "season not found for series with id: " + traktId);
            }
            cursor.close();
        } else {
            mLogger.warn(LOG_TAG, "query returned null");
        }
        return seasons;
    }

    @Override
    public int insertAllSeasons(List<Season> seasons) {
        mLogger.debug(LOG_TAG, "inserting seasons");

        Vector<ContentValues> cVVector = new Vector<>();

        for (Season season : seasons) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SEASON_SERIES_ID, season.getSeriesId());
            values.put(COLUMN_NUMBER, season.getNumber());

            if (season.getImages() != null) {
                if (season.getImages().getPoster() != null) {
                    values.put(COLUMN_POSTER_FULL, season.getImages().getPoster().getFull());
                    values.put(COLUMN_POSTER_THUMB, season.getImages().getPoster().getThumb());
                }
                if (season.getImages().getThumb() != null) {
                    values.put(COLUMN_THUMB, season.getImages().getThumb().getFull());
                }
            }

            cVVector.add(values);
        }

        int rowsInserted = 0;

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            //Insert all the data into the database
            rowsInserted = mContentResolver.bulkInsert(SeasonsEntry.CONTENT_URI, cvArray);
        }

        Log.v(LOG_TAG, "inserted " + rowsInserted + " rows into seasons table");
        return rowsInserted;
    }

    @Override
    public int insertAllEpisodes(List<Episode> episodes) {

        mLogger.debug(LOG_TAG, "inserting episodes");

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        db.beginTransaction();
        int returnCount = 0;
        try {
            for (Episode episode : episodes) {

                if (episode.getImages() == null) {
                    episode.setImages(new Images());
                }

                if (episode.getImages().getScreenshot() == null) {
                    episode.getImages().setScreenshot(new Image());
                }

                long firstAired = -1;
                if (episode.getFirstAired() != null) {
                    firstAired = episode.getFirstAired().getMillis();
                }

                db.execSQL("INSERT OR REPLACE INTO episodes (" +

                                EpisodesEntry.COLUMN_SERIES_ID + ", " +
                                EpisodesEntry.COLUMN_SEASON + ", " +
                                EpisodesEntry.COLUMN_EPISODE_NUMBER + ", " +
                                EpisodesEntry.COLUMN_TITLE + ", " +
                                EpisodesEntry.COLUMN_TRAKT_ID + ", " +
                                EpisodesEntry.COLUMN_TVDB_ID + ", " +
                                EpisodesEntry.COLUMN_IMDB_ID + ", " +
                                EpisodesEntry.COLUMN_TMDB_ID + ", " +
                                EpisodesEntry.COLUMN_TV_RAGE_ID + ", " +
                                EpisodesEntry.COLUMN_SLUG + ", " +
                                EpisodesEntry.COLUMN_ABSOLUTE_NUMBER + ", " +
                                EpisodesEntry.COLUMN_OVERVIEW + ", " +
                                EpisodesEntry.COLUMN_TRAKT_RATING + ", " +
                                EpisodesEntry.COLUMN_TRAKT_RATING_COUNT + ", " +
                                EpisodesEntry.COLUMN_FIRST_AIRED + ", " +
                                EpisodesEntry.COLUMN_TVDB_RATING + ", " +
                                EpisodesEntry.COLUMN_SCREENSHOT_FULL + ", " +
                                EpisodesEntry.COLUMN_SCREENSHOT_THUMB + ", " +
                                EpisodesEntry.COLUMN_WATCHED + ", " +
                                EpisodesEntry.COLUMN_LIKED + ") " +

                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                                " (SELECT watched FROM episodes WHERE trakt_id = ?), " +
                                " (SELECT liked FROM episodes WHERE trakt_id = ?))",
                        new String[]{
                                String.valueOf(episode.getSeriesId()),
                                String.valueOf(episode.getSeason()),
                                String.valueOf(episode.getNumber()),
                                episode.getTitle(),
                                String.valueOf(episode.getTraktId()),
                                String.valueOf(episode.getTvdbId()),
                                episode.getImdbId(),
                                String.valueOf(episode.getTmdbId()),
                                String.valueOf(episode.getTvRageId()),
                                episode.getSlugName(),
                                String.valueOf(episode.getAbsoluteNumber()),
                                episode.getOverview(),
                                String.valueOf(episode.getTraktRating()),
                                String.valueOf(episode.getTraktRatingCount()),
                                String.valueOf(firstAired),
                                null,
                                episode.getImages().getScreenshot().getFull(),
                                episode.getImages().getScreenshot().getThumb(),
                                String.valueOf(episode.getTraktId()),
                                String.valueOf(episode.getTraktId())
                        }
                );
                returnCount++;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        Log.v(LOG_TAG, "inserted " + returnCount + " rows into episodes table");
        return returnCount;
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
    public boolean isWatched(int traktId) {
        mLogger.debug(LOG_TAG, "is episode(" + traktId + ") watched?");
        Cursor cursor = mContentResolver.query(
                ActivityEntry.CONTENT_URI,
                null,
                ActivityEntry.COLUMN_EPISODE_ID + " = ? AND " +
                        ActivityEntry.COLUMN_ACTIVITY_TYPE + " = ?",
                new String[]{String.valueOf(traktId), String.valueOf(ActivityType.WATCHED)},
                null
        );

        boolean watched = false;

        if (cursor != null) {
            watched = cursor.moveToFirst();
            cursor.close();
        } else {
            mLogger.warn(LOG_TAG, "query returned null");
        }
        return watched;
    }

    @Override
    public boolean setWatched(int traktId, boolean watched) {
        if (watched) {
            mLogger.debug(LOG_TAG, "saving watched activity to episode: " + traktId);
            ContentValues values = new ContentValues();
            values.put(ActivityEntry.COLUMN_EPISODE_ID, traktId);
            values.put(ActivityEntry.COLUMN_TIMESTAMP, System.currentTimeMillis());
            values.put(ActivityEntry.COLUMN_ACTIVITY_TYPE, ActivityType.WATCHED);

            int rowsDeleted = mContentResolver.delete(
                    ActivityEntry.CONTENT_URI,
                    ActivityEntry.COLUMN_EPISODE_ID + " = ? AND " +
                            ActivityEntry.COLUMN_EPISODE_ID + " = ?",
                    new String[]{String.valueOf(traktId), String.valueOf(ActivityType.SKIPPED)}
            );

            mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from activity table");

            Uri uri = mContentResolver.insert(ActivityEntry.CONTENT_URI, values);

            if (uri != null) {
                mLogger.verbose(LOG_TAG, "inserted 1 rows into activity table");
                return true;
            } else {
                mLogger.verbose(LOG_TAG, "failed to insert int activity table");
                return false;
            }
        } else {
            int rowsDeleted = mContentResolver.delete(
                    ActivityEntry.CONTENT_URI,
                    ActivityEntry.COLUMN_EPISODE_ID + " = ? AND " +
                            ActivityEntry.COLUMN_EPISODE_ID + " = ?",
                    new String[]{String.valueOf(traktId), String.valueOf(ActivityType.WATCHED)}
            );
            mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from activity table");
            return true;
        }
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
    public boolean setLiked(int traktId, boolean liked) {
        mLogger.debug(LOG_TAG, "setting 'liked' of episode(" + traktId + ") to " + liked);
        if (liked) {
            ContentValues values = new ContentValues();
            values.put(ActivityEntry.COLUMN_EPISODE_ID, traktId);
            values.put(ActivityEntry.COLUMN_TIMESTAMP, System.currentTimeMillis());
            values.put(ActivityEntry.COLUMN_ACTIVITY_TYPE, ActivityType.LIKED);

            Uri uri = mContentResolver.insert(ActivityEntry.CONTENT_URI, values);

            if (uri != null) {
                mLogger.verbose(LOG_TAG, "inserted 1 rows into activity table");
                return true;
            } else {
                mLogger.verbose(LOG_TAG, "failed to insert int activity table");
                return false;
            }
        } else {
            int rowsDeleted = mContentResolver.delete(
                    ActivityEntry.CONTENT_URI,
                    ActivityEntry.COLUMN_EPISODE_ID + " = ? AND " +
                            ActivityEntry.COLUMN_EPISODE_ID + " = ?",
                    new String[]{String.valueOf(traktId), String.valueOf(ActivityType.LIKED)}
            );
            mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from activity table");
            return true;
        }
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
            //noinspection WrongConstant
            episode.setWatched(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(COLUMN_LIKED);
        if (column != -1) {
            episode.setLiked(cursor.getInt(column) == 1);
        }

        return episode;
    }


    private Season mapCursorToSeason(Cursor cursor) {
        Season season = new Season();

        season.setImages(new Images());
        season.getImages().setPoster(new Image());
        season.getImages().setThumb(new Image());

        int column;

        column = cursor.getColumnIndex(EpisodeDao.COLUMN_SEASON_SERIES_ID);
        if (column != -1) {
            season.setSeriesId(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(EpisodeDao.COLUMN_NUMBER);
        if (column != -1) {
            season.setNumber(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(EpisodeDao.COLUMN_POSTER_FULL);
        if (column != -1) {
            season.getImages().getPoster().setFull(cursor.getString(column));
        }

        column = cursor.getColumnIndex(EpisodeDao.COLUMN_POSTER_THUMB);
        if (column != -1) {
            season.getImages().getPoster().setThumb(cursor.getString(column));
        }

        column = cursor.getColumnIndex(EpisodeDao.COLUMN_THUMB);
        if (column != -1) {
            season.getImages().getThumb().setFull(cursor.getString(column));
        }

        column = cursor.getColumnIndex(EpisodeDao.COLUMN_WATCH_COUNT);
        if (column != -1) {
            season.setWatchCount(cursor.getInt(column));
        }

        column = cursor.getColumnIndex(EpisodeDao.COLUMN_SKIP_COUNT);
        if (column != -1) {
            season.setSkipCount(cursor.getInt(column));
        }

        return season;
    }
}
