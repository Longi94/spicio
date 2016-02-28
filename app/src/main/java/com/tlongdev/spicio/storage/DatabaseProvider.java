package com.tlongdev.spicio.storage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FeedEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FriendsEntry;
import com.tlongdev.spicio.storage.DatabaseContract.SeriesEntry;

/**
 * Outer Layer, Storage.
 *
 * @author Long
 * @since 2016. 02. 26.
 */
public class DatabaseProvider extends ContentProvider {

    public static final int SERIES = 100;
    public static final int EPISODES = 101;
    public static final int FEED = 102;
    public static final int FRIENDS = 103;

    /**
     * The URI Matcher used by this content provider
     */
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
     * The database helper.
     */
    private DatabaseHelper mOpenHelper;

    /**
     * Builds an URI matcher to match the given URIs
     *
     * @return the URI matcher
     */
    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, DatabaseContract.PATH_SERIES, SERIES);
        matcher.addURI(authority, DatabaseContract.PATH_EPISODES, EPISODES);
        matcher.addURI(authority, DatabaseContract.PATH_FEED, FEED);
        matcher.addURI(authority, DatabaseContract.PATH_FRIENDS, FRIENDS);

        return matcher;
    }

    public DatabaseProvider() {
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        String tableName;
        switch (sUriMatcher.match(uri)) {
            case SERIES:
                tableName = SeriesEntry.TABLE_NAME;
                break;
            case EPISODES:
                tableName = EpisodesEntry.TABLE_NAME;
                break;
            case FEED:
                tableName = FeedEntry.TABLE_NAME;
                break;
            case FRIENDS:
                tableName = FriendsEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor = mOpenHelper.getReadableDatabase().query(
                tableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        if (retCursor != null)
            //noinspection ConstantConditions
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long _id;

        switch (sUriMatcher.match(uri)) {
            case SERIES:
                _id = db.insert(SeriesEntry.TABLE_NAME, null, values);
                returnUri = SeriesEntry.buildUri(_id);
                break;
            case EPISODES:
                _id = db.insert(EpisodesEntry.TABLE_NAME, null, values);
                returnUri = EpisodesEntry.buildUri(_id);
                break;
            case FEED:
                _id = db.insert(FeedEntry.TABLE_NAME, null, values);
                returnUri = FeedEntry.buildUri(_id);
                break;
            case FRIENDS:
                _id = db.insert(FriendsEntry.TABLE_NAME, null, values);
                returnUri = FriendsEntry.buildUri(_id);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (_id > 0)
            return returnUri;
        else
            throw new android.database.SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case SERIES:
                rowsUpdated = db.update(SeriesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case EPISODES:
                rowsUpdated = db.update(EpisodesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FEED:
                rowsUpdated = db.update(FeedEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FRIENDS:
                rowsUpdated = db.update(FriendsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case SERIES:
                rowsDeleted = db.delete(SeriesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EPISODES:
                rowsDeleted = db.delete(EpisodesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FEED:
                rowsDeleted = db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FRIENDS:
                rowsDeleted = db.delete(FriendsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String tableName;

        switch (sUriMatcher.match(uri)) {
            case SERIES:
                tableName = SeriesEntry.TABLE_NAME;
                break;
            case EPISODES:
                tableName = EpisodesEntry.TABLE_NAME;
                break;
            case FEED:
                tableName = FeedEntry.TABLE_NAME;
                break;
            case FRIENDS:
                tableName = FriendsEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(tableName, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        //noinspection ConstantConditions
        getContext().getContentResolver().notifyChange(uri, null);

        return returnCount;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case SERIES:
                return "vnd.android.cursor.dir/" + DatabaseContract.CONTENT_AUTHORITY + "/" + DatabaseContract.PATH_SERIES;
            case EPISODES:
                return "vnd.android.cursor.dir/" + DatabaseContract.CONTENT_AUTHORITY + "/" + DatabaseContract.PATH_EPISODES;
            case FEED:
                return "vnd.android.cursor.dir/" + DatabaseContract.CONTENT_AUTHORITY + "/" + DatabaseContract.PATH_FEED;
            case FRIENDS:
                return "vnd.android.cursor.dir/" + DatabaseContract.CONTENT_AUTHORITY + "/" + DatabaseContract.PATH_FRIENDS;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

}
