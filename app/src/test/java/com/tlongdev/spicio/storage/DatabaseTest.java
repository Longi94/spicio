package com.tlongdev.spicio.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tlongdev.spicio.BuildConfig;
import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FeedEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FriendsEntry;
import com.tlongdev.spicio.storage.DatabaseContract.SeriesEntry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Long
 * @since 2016. 02. 27.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseTest {

    private Context mContext;

    @Before
    public void setUp() throws Exception {
        assertNotNull(RuntimeEnvironment.application);

        mContext = RuntimeEnvironment.application;
        mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateDatabase() {

        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(SeriesEntry.TABLE_NAME);
        tableNameHashSet.add(EpisodesEntry.TABLE_NAME);
        tableNameHashSet.add(FeedEntry.TABLE_NAME);
        tableNameHashSet.add(FriendsEntry.TABLE_NAME);

        SQLiteDatabase db = new DatabaseHelper(mContext).getWritableDatabase();

        assertNotNull(db);
        assertTrue(db.isOpen());

        //See if we've created the tables we want
        Cursor tables = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue(tables.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(tables.getString(0));
        } while (tables.moveToNext());

        tables.close();

        assertTrue("Error: Your database was created with missing tables", tableNameHashSet.isEmpty());

        Cursor columns = db.rawQuery("PRAGMA table_info(" + SeriesEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                columns.moveToFirst());

        //Check for table columns
        final HashSet<String> columnHashSet = new HashSet<String>();
        columnHashSet.add(SeriesEntry.COLUMN_TITLE);
        columnHashSet.add(SeriesEntry.COLUMN_YEAR);
        columnHashSet.add(SeriesEntry.COLUMN_TRAKT_ID);
        columnHashSet.add(SeriesEntry.COLUMN_TVDB_ID);
        columnHashSet.add(SeriesEntry.COLUMN_IMDB_ID);
        columnHashSet.add(SeriesEntry.COLUMN_TMDB_ID);
        columnHashSet.add(SeriesEntry.COLUMN_TV_RAGE_ID);
        columnHashSet.add(SeriesEntry.COLUMN_SLUG);
        columnHashSet.add(SeriesEntry.COLUMN_OVERVIEW);
        columnHashSet.add(SeriesEntry.COLUMN_FIRST_AIRED);
        columnHashSet.add(SeriesEntry.COLUMN_DAY_OF_AIR);
        columnHashSet.add(SeriesEntry.COLUMN_TIME_OF_AIR);
        columnHashSet.add(SeriesEntry.COLUMN_AIR_TIME_ZONE);
        columnHashSet.add(SeriesEntry.COLUMN_RUNTIME);
        columnHashSet.add(SeriesEntry.COLUMN_CONTENT_RATING);
        columnHashSet.add(SeriesEntry.COLUMN_NETWORK);
        columnHashSet.add(SeriesEntry.COLUMN_TRAILER);
        columnHashSet.add(SeriesEntry.COLUMN_STATUS);
        columnHashSet.add(SeriesEntry.COLUMN_TRAKT_RATING);
        columnHashSet.add(SeriesEntry.COLUMN_TRAKT_RATING_COUNT);
        columnHashSet.add(SeriesEntry.COLUMN_GENRES);
        columnHashSet.add(SeriesEntry.COLUMN_POSTER_FULL);
        columnHashSet.add(SeriesEntry.COLUMN_POSTER_THUMB);
        columnHashSet.add(SeriesEntry.COLUMN_THUMB);

        columnHashSet.add(SeriesEntry.COLUMN_TVDB_RATING);
        columnHashSet.add(SeriesEntry.COLUMN_TVDB_RATING_COUNT);

        int columnNameIndex = columns.getColumnIndex("name");

        do {
            String columnName = columns.getString(columnNameIndex);
            columnHashSet.remove(columnName);
        } while (columns.moveToNext());

        columns.close();

        assertTrue("Error: The database doesn't contain all of the required columns",
                columnHashSet.isEmpty());

        columns = db.rawQuery("PRAGMA table_info(" + EpisodesEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                columns.moveToFirst());

        //Check for table columns
        columnHashSet.clear();

        columnHashSet.add(EpisodesEntry.COLUMN_SERIES_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_SEASON);
        columnHashSet.add(EpisodesEntry.COLUMN_EPISODE_NUMBER);
        columnHashSet.add(EpisodesEntry.COLUMN_TITLE);
        columnHashSet.add(EpisodesEntry.COLUMN_TRAKT_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_TVDB_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_IMDB_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_TMDB_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_TV_RAGE_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_SLUG);
        columnHashSet.add(EpisodesEntry.COLUMN_ABSOLUTE_NUMBER);
        columnHashSet.add(EpisodesEntry.COLUMN_OVERVIEW);
        columnHashSet.add(EpisodesEntry.COLUMN_TRAKT_RATING);
        columnHashSet.add(EpisodesEntry.COLUMN_TRAKT_RATING_COUNT);
        columnHashSet.add(EpisodesEntry.COLUMN_FIRST_AIRED);
        columnHashSet.add(EpisodesEntry.COLUMN_SCREENSHOT_FULL);
        columnHashSet.add(EpisodesEntry.COLUMN_SCREENSHOT_THUMB);

        columnHashSet.add(EpisodesEntry.COLUMN_TVDB_RATING);

        do {
            String columnName = columns.getString(columnNameIndex);
            columnHashSet.remove(columnName);
        } while (columns.moveToNext());

        columns.close();

        assertTrue("Error: The database doesn't contain all of the required columns",
                columnHashSet.isEmpty());

        columns = db.rawQuery("PRAGMA table_info(" + FeedEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                columns.moveToFirst());

        //Check for table columns
        columnHashSet.clear();
        columnHashSet.add(FeedEntry.COLUMN_CULPRIT_ID);
        columnHashSet.add(FeedEntry.COLUMN_EPISODE_ABSOLUTE_NUMBER);
        columnHashSet.add(FeedEntry.COLUMN_EPISODE_ID);
        columnHashSet.add(FeedEntry.COLUMN_EPISODE_NAME);
        columnHashSet.add(FeedEntry.COLUMN_EPISODE_NUMBER);
        columnHashSet.add(FeedEntry.COLUMN_ITEM_ID);
        columnHashSet.add(FeedEntry.COLUMN_SEASON_NUMBER);
        columnHashSet.add(FeedEntry.COLUMN_SERIES_ID);
        columnHashSet.add(FeedEntry.COLUMN_SERIES_NAME);
        columnHashSet.add(FeedEntry.COLUMN_TIMESTAMP);
        columnHashSet.add(FeedEntry.COLUMN_TYPE);
        columnHashSet.add(FeedEntry.COLUMN_VICTIM_ID);
        columnHashSet.add(FeedEntry.COLUMN_VICTIM_NAME);

        do {
            String columnName = columns.getString(columnNameIndex);
            columnHashSet.remove(columnName);
        } while (columns.moveToNext());

        columns.close();

        assertTrue("Error: The database doesn't contain all of the required columns",
                columnHashSet.isEmpty());

        columns = db.rawQuery("PRAGMA table_info(" + FriendsEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                columns.moveToFirst());

        //Check for table columns
        columnHashSet.clear();
        columnHashSet.add(FriendsEntry.COLUMN_AVATAR);
        columnHashSet.add(FriendsEntry.COLUMN_NAME);
        columnHashSet.add(FriendsEntry.COLUMN_USER_ID);

        do {
            String columnName = columns.getString(columnNameIndex);
            columnHashSet.remove(columnName);
        } while (columns.moveToNext());

        columns.close();

        assertTrue("Error: The database doesn't contain all of the required columns",
                columnHashSet.isEmpty());
    }
}
