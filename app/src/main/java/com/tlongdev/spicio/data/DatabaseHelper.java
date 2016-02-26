package com.tlongdev.spicio.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tlongdev.spicio.data.DatabaseContract.*;

/**
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

                SeriesEntry.COLUMN_SERIES_ID + " INTEGER NOT NULL, " +
                SeriesEntry.COLUMN_ACTORS + " TEXT, " +
                SeriesEntry.COLUMN_AIRS_DAY + " INTEGER, " +
                SeriesEntry.COLUMN_AIRS_TIME + " INTEGER, " +
                SeriesEntry.COLUMN_CONTENT_RATING + " TEXT, " +
                SeriesEntry.COLUMN_FIRST_AIRED + " INTEGER, " +
                SeriesEntry.COLUMN_GENRE + " TEXT, " +
                SeriesEntry.COLUMN_IMDB_ID + " TEXT, " +
                SeriesEntry.COLUMN_NETWORK + " TEXT, " +
                SeriesEntry.COLUMN_OVERVIEW + " TEXT, " +
                SeriesEntry.COLUMN_TVDB_RATING + " REAL, " +
                SeriesEntry.COLUMN_TVDB_RATING_COUNT + " INTEGER, " +
                SeriesEntry.COLUMN_RUNTIME + " INTEGER, " +
                SeriesEntry.COLUMN_NAME + " TEXT, " +
                SeriesEntry.COLUMN_STATUS + " INTEGER, " +
                SeriesEntry.COLUMN_BANNER + " TEXT, " +
                SeriesEntry.COLUMN_POSTER + " TEXT, " +
                SeriesEntry.COLUMN_ZAPT2IT_ID + " TEXT, " +
                SeriesEntry.COLUMN_ALIAS_NAMES + " TEXT, " +

                "UNIQUE (" + SeriesEntry.COLUMN_SERIES_ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_EPISODES_TABLE = "CREATE TABLE " + EpisodesEntry.TABLE_NAME + " (" +
                EpisodesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                EpisodesEntry.COLUMN_EPISODE_ID + " INTEGER NOT NULL, " +
                EpisodesEntry.COLUMN_SEASON_ID + " INTEGER NOT NULL, " +
                EpisodesEntry.COLUMN_EPISODE_NUMBER + " INTEGER, " +
                EpisodesEntry.COLUMN_EPISODE_NAME + " TEXT, " +
                EpisodesEntry.COLUMN_GUEST_STARS + " TEXT, " +
                EpisodesEntry.COLUMN_DIRECTOR + " TEXT, " +
                EpisodesEntry.COLUMN_WRITERS + " TEXT, " +
                EpisodesEntry.COLUMN_OVERVIEW + " TEXT, " +
                EpisodesEntry.COLUMN_SEASON_NUMBER + " INTEGER, " +
                EpisodesEntry.COLUMN_ABSOLUTE_NUMBER + " INTEGER NOT NULL, " +
                EpisodesEntry.COLUMN_IMAGE + " TEXT, " +
                EpisodesEntry.COLUMN_SERIES_ID + " TEXT NOT NULL, " +
                EpisodesEntry.COLUMN_IMDB_ID + " TEXT, " +
                EpisodesEntry.COLUMN_TVDB_RATING + " REAL, " +

                "UNIQUE (" + EpisodesEntry.COLUMN_EPISODE_ID + ") ON CONFLICT REPLACE," +
                "UNIQUE (" + EpisodesEntry.COLUMN_EPISODE_NUMBER + ", " +
                EpisodesEntry.COLUMN_SEASON_NUMBER + ", " +
                EpisodesEntry.COLUMN_SERIES_ID + ") ON CONFLICT REPLACE," +
                "UNIQUE (" + EpisodesEntry.COLUMN_ABSOLUTE_NUMBER + ", " +
                EpisodesEntry.COLUMN_SERIES_ID + ") ON CONFLICT REPLACE);";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SeriesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EpisodesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FriendsEntry.TABLE_NAME);

        onCreate(db);
    }
}
