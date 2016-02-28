package com.tlongdev.spicio.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FeedEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FriendsEntry;
import com.tlongdev.spicio.storage.DatabaseContract.SeriesEntry;

import java.util.HashSet;

/**
 * @author Long
 * @since 2016. 02. 27.
 */
public class DatabaseTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

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
        columnHashSet.add(SeriesEntry.COLUMN_ACTORS);
        columnHashSet.add(SeriesEntry.COLUMN_AIRS_DAY);
        columnHashSet.add(SeriesEntry.COLUMN_AIRS_TIME);
        columnHashSet.add(SeriesEntry.COLUMN_ALIAS_NAMES);
        columnHashSet.add(SeriesEntry.COLUMN_BANNER);
        columnHashSet.add(SeriesEntry.COLUMN_CONTENT_RATING);
        columnHashSet.add(SeriesEntry.COLUMN_FIRST_AIRED);
        columnHashSet.add(SeriesEntry.COLUMN_GENRE);
        columnHashSet.add(SeriesEntry.COLUMN_IMDB_ID);
        columnHashSet.add(SeriesEntry.COLUMN_NAME);
        columnHashSet.add(SeriesEntry.COLUMN_NETWORK);
        columnHashSet.add(SeriesEntry.COLUMN_OVERVIEW);
        columnHashSet.add(SeriesEntry.COLUMN_POSTER);
        columnHashSet.add(SeriesEntry.COLUMN_RUNTIME);
        columnHashSet.add(SeriesEntry.COLUMN_SERIES_ID);
        columnHashSet.add(SeriesEntry.COLUMN_STATUS);
        columnHashSet.add(SeriesEntry.COLUMN_TVDB_RATING);
        columnHashSet.add(SeriesEntry.COLUMN_TVDB_RATING_COUNT);
        columnHashSet.add(SeriesEntry.COLUMN_ZAPT2IT_ID);

        int columnNameIndex = columns.getColumnIndex("name");

        do {
            String columnName = columns.getString(columnNameIndex);
            columnHashSet.remove(columnName);
        } while(columns.moveToNext());

        columns.close();

        assertTrue("Error: The database doesn't contain all of the required columns",
                columnHashSet.isEmpty());

        columns = db.rawQuery("PRAGMA table_info(" + EpisodesEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                columns.moveToFirst());

        //Check for table columns
        columnHashSet.clear();
        columnHashSet.add(EpisodesEntry.COLUMN_ABSOLUTE_NUMBER);
        columnHashSet.add(EpisodesEntry.COLUMN_DIRECTOR);
        columnHashSet.add(EpisodesEntry.COLUMN_EPISODE_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_EPISODE_NAME);
        columnHashSet.add(EpisodesEntry.COLUMN_EPISODE_NUMBER);
        columnHashSet.add(EpisodesEntry.COLUMN_GUEST_STARS);
        columnHashSet.add(EpisodesEntry.COLUMN_IMAGE);
        columnHashSet.add(EpisodesEntry.COLUMN_IMDB_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_OVERVIEW);
        columnHashSet.add(EpisodesEntry.COLUMN_SEASON_ID);
        columnHashSet.add(EpisodesEntry.COLUMN_SEASON_NUMBER);
        columnHashSet.add(EpisodesEntry.COLUMN_TVDB_RATING);
        columnHashSet.add(EpisodesEntry.COLUMN_WRITERS);

        do {
            String columnName = columns.getString(columnNameIndex);
            columnHashSet.remove(columnName);
        } while(columns.moveToNext());

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
        } while(columns.moveToNext());

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
        } while(columns.moveToNext());

        columns.close();

        assertTrue("Error: The database doesn't contain all of the required columns",
                columnHashSet.isEmpty());
    }
}
