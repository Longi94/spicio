package com.tlongdev.spicio.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tlongdev.spicio.storage.DatabaseContract.*;

/**
 * Outer Layer, Storage.
 *
 * @author Long
 * @since 2016. 02. 26.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "spicio.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SERIES_TABLE = "CREATE TABLE " + SeriesEntry.TABLE_NAME + " (" +
                SeriesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                SeriesEntry.COLUMN_TITLE + " TEXT, " +
                SeriesEntry.COLUMN_YEAR + " INTEGER, " +
                SeriesEntry.COLUMN_TRAKT_ID + " INTEGER NOT NULL, " +
                SeriesEntry.COLUMN_TVDB_ID + " INTEGER, " +
                SeriesEntry.COLUMN_IMDB_ID + " TEXT, " +
                SeriesEntry.COLUMN_TMDB_ID + " INTEGER, " +
                SeriesEntry.COLUMN_TV_RAGE_ID + " INTEGER, " +
                SeriesEntry.COLUMN_SLUG + " TEXT, " +
                SeriesEntry.COLUMN_OVERVIEW + " TEXT, " +
                SeriesEntry.COLUMN_FIRST_AIRED + " INTEGER, " +
                SeriesEntry.COLUMN_DAY_OF_AIR + " INTEGER, " +
                SeriesEntry.COLUMN_TIME_OF_AIR + " INTEGER, " +
                SeriesEntry.COLUMN_AIR_TIME_ZONE + " TEXT, " +
                SeriesEntry.COLUMN_RUNTIME + " INTEGER, " +
                SeriesEntry.COLUMN_CONTENT_RATING + " TEXT, " +
                SeriesEntry.COLUMN_NETWORK + " TEXT, " +
                SeriesEntry.COLUMN_TRAILER + " TEXT, " +
                SeriesEntry.COLUMN_STATUS + " INTEGER, " +
                SeriesEntry.COLUMN_TRAKT_RATING + " REAL, " +
                SeriesEntry.COLUMN_TRAKT_RATING_COUNT + " INTEGER, " +
                SeriesEntry.COLUMN_GENRES + " TEXT, " +
                SeriesEntry.COLUMN_TVDB_RATING + " REAL, " +
                SeriesEntry.COLUMN_TVDB_RATING_COUNT + " INTEGER, " +
                SeriesEntry.COLUMN_POSTER_FULL + " TEXT, " +
                SeriesEntry.COLUMN_POSTER_THUMB + " TEXT, " +
                SeriesEntry.COLUMN_THUMB + " TEXT, " +

                "UNIQUE (" + SeriesEntry.COLUMN_TRAKT_ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_EPISODES_TABLE = "CREATE TABLE " + EpisodesEntry.TABLE_NAME + " (" +
                EpisodesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                EpisodesEntry.COLUMN_SERIES_ID + " INTEGER NOT NULL, " +
                EpisodesEntry.COLUMN_SEASON + " INTEGER NOT NULL, " +
                EpisodesEntry.COLUMN_EPISODE_NUMBER + " INTEGER NOT NULL, " +
                EpisodesEntry.COLUMN_TITLE + " TEXT, " +
                EpisodesEntry.COLUMN_TRAKT_ID + " INTEGER NOT NULL, " +
                EpisodesEntry.COLUMN_TVDB_ID + " INTEGER, " +
                EpisodesEntry.COLUMN_IMDB_ID + " TEXT, " +
                EpisodesEntry.COLUMN_TMDB_ID + " INTEGER, " +
                EpisodesEntry.COLUMN_TV_RAGE_ID + " INTEGER, " +
                EpisodesEntry.COLUMN_SLUG + " TEXT, " +
                EpisodesEntry.COLUMN_ABSOLUTE_NUMBER + " INTEGER, " +
                EpisodesEntry.COLUMN_OVERVIEW + " TEXT, " +
                EpisodesEntry.COLUMN_TRAKT_RATING + " REAL, " +
                EpisodesEntry.COLUMN_TRAKT_RATING_COUNT + " INTEGER, " +
                EpisodesEntry.COLUMN_SERIES_TRAKT_ID + " INTEGER, " +
                EpisodesEntry.COLUMN_TVDB_RATING + " REAL, " +
                EpisodesEntry.COLUMN_SCREENSHOT_FULL + " TEXT, " +
                EpisodesEntry.COLUMN_SCREENSHOT_THUMB + " TEXT, " +
                EpisodesEntry.COLUMN_WATCHED + " INTEGER NOT NULL DEFAULT 0, " +
                EpisodesEntry.COLUMN_LIKED + " INTEGER NOT NULL DEFAULT 0, " +
                EpisodesEntry.COLUMN_SKIPPED + " INTEGER NOT NULL DEFAULT 0, " +

                "UNIQUE (" + EpisodesEntry.COLUMN_TRAKT_ID + ") ON CONFLICT REPLACE," +
                "UNIQUE (" + EpisodesEntry.COLUMN_SERIES_ID + ", " + EpisodesEntry.COLUMN_SEASON +
                ", " + EpisodesEntry.COLUMN_EPISODE_NUMBER + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_SEASONS_TABLE = "CREATE TABLE " + SeasonsEntry.TABLE_NAME + " (" +
                SeasonsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                SeasonsEntry.COLUMN_SERIES_ID + " INTEGER NOT NULL, " +
                SeasonsEntry.COLUMN_NUMBER + " INTEGER NOT NULL, " +
                SeasonsEntry.COLUMN_POSTER_FULL + " TEXT, " +
                SeasonsEntry.COLUMN_POSTER_THUMB + " TEXT, " +
                SeasonsEntry.COLUMN_THUMB + " TEXT, " +

                "UNIQUE (" + SeasonsEntry.COLUMN_SERIES_ID + ", " + SeasonsEntry.COLUMN_NUMBER + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_FEED_TABLE = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                FeedEntry.COLUMN_ITEM_ID + " INTEGER NOT NULL, " +
                FeedEntry.COLUMN_TYPE + " INTEGER, " +
                FeedEntry.COLUMN_TIMESTAMP + " INTEGER, " +
                FeedEntry.COLUMN_CULPRIT_ID + " INTEGER, " +
                FeedEntry.COLUMN_VICTIM_ID + " INTEGER, " +
                FeedEntry.COLUMN_VICTIM_NAME + " TEXT, " +
                FeedEntry.COLUMN_SERIES_ID + " INTEGER, " +
                FeedEntry.COLUMN_SERIES_NAME + " TEXT, " +
                FeedEntry.COLUMN_EPISODE_ID + " INTEGER, " +
                FeedEntry.COLUMN_EPISODE_NAME + " TEXT, " +
                FeedEntry.COLUMN_EPISODE_NUMBER + " INTEGER, " +
                FeedEntry.COLUMN_EPISODE_ABSOLUTE_NUMBER + " INTEGER, " +
                FeedEntry.COLUMN_SEASON_NUMBER + " INTEGER, " +

                "UNIQUE (" + FeedEntry.COLUMN_ITEM_ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_FRIENDS_TABLE = "CREATE TABLE " + FriendsEntry.TABLE_NAME + " (" +
                FriendsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                FriendsEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                FriendsEntry.COLUMN_NAME + " TEXT, " +
                FriendsEntry.COLUMN_AVATAR + " TEXT, " +

                "UNIQUE (" + FriendsEntry.COLUMN_USER_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_SERIES_TABLE);
        db.execSQL(SQL_CREATE_EPISODES_TABLE);
        db.execSQL(SQL_CREATE_FEED_TABLE);
        db.execSQL(SQL_CREATE_FRIENDS_TABLE);
        db.execSQL(SQL_CREATE_SEASONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SeriesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EpisodesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FriendsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SeasonsEntry.TABLE_NAME);

        onCreate(db);
    }
}
