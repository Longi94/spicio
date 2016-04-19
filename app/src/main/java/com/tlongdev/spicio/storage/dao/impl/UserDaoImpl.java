package com.tlongdev.spicio.storage.dao.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.ActivityType;
import com.tlongdev.spicio.domain.model.SeriesActivities;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.storage.DatabaseContract.ActivityEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FriendsEntry;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public class UserDaoImpl implements UserDao {

    private static final String LOG_TAG = UserDaoImpl.class.getSimpleName();

    @Inject ContentResolver mContentResolver;
    @Inject Logger mLogger;

    public UserDaoImpl(SpicioApplication application) {
        application.getStorageComponent().inject(this);
    }

    @Override
    public int insertUserActivities(Map<Integer, SeriesActivities> activitiesMap) {

        Vector<ContentValues> cVVector = new Vector<>();

        for (Map.Entry<Integer, SeriesActivities> series : activitiesMap.entrySet()) {

            for (Map.Entry<Integer, Long> episode : series.getValue().getWatched().entrySet()) {
                ContentValues values = new ContentValues();
                values.put(ActivityEntry.COLUMN_ACTIVITY_TYPE, ActivityType.WATCHED);
                values.put(ActivityEntry.COLUMN_SERIES_ID, series.getKey());
                values.put(ActivityEntry.COLUMN_EPISODE_ID, episode.getKey());
                values.put(ActivityEntry.COLUMN_TIMESTAMP, episode.getValue());

                cVVector.add(values);
            }

            for (Map.Entry<Integer, Long> episode : series.getValue().getSkipped().entrySet()) {
                ContentValues values = new ContentValues();
                values.put(ActivityEntry.COLUMN_ACTIVITY_TYPE, ActivityType.SKIPPED);
                values.put(ActivityEntry.COLUMN_SERIES_ID, series.getKey());
                values.put(ActivityEntry.COLUMN_EPISODE_ID, episode.getKey());
                values.put(ActivityEntry.COLUMN_TIMESTAMP, episode.getValue());

                cVVector.add(values);
            }

            for (Map.Entry<Integer, Long> episode : series.getValue().getLiked().entrySet()) {
                ContentValues values = new ContentValues();
                values.put(ActivityEntry.COLUMN_ACTIVITY_TYPE, ActivityType.LIKED);
                values.put(ActivityEntry.COLUMN_SERIES_ID, series.getKey());
                values.put(ActivityEntry.COLUMN_EPISODE_ID, episode.getKey());
                values.put(ActivityEntry.COLUMN_TIMESTAMP, episode.getValue());

                cVVector.add(values);
            }
        }

        int rowsInserted = 0;

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            //Insert all the data into the database
            rowsInserted = mContentResolver.bulkInsert(ActivityEntry.CONTENT_URI, cvArray);
        }

        mLogger.verbose(LOG_TAG, "inserted " + rowsInserted + " rows into activity table");

        return rowsInserted;
    }

    public List<User> getFriends() {
        Cursor cursor = mContentResolver.query(
                FriendsEntry.CONTENT_URI,
                null,
                null,
                null,
                FriendsEntry.COLUMN_NAME + " ASC"
        );

        List<User> friends = new LinkedList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getLong(cursor.getColumnIndex(FriendsEntry.COLUMN_USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME)));

                friends.add(user);
            }
            cursor.close();
        }

        return friends;
    }

    @Override
    public Uri addFriend(User friend) {
        ContentValues values = new ContentValues();

        values.put(FriendsEntry.COLUMN_USER_ID, friend.getId());
        values.put(FriendsEntry.COLUMN_NAME, friend.getName());

        Uri uri = mContentResolver.insert(FriendsEntry.CONTENT_URI, values);

        if (uri != null) {
            mLogger.verbose(LOG_TAG, "inserted 1 row into friends table");
        } else {
            mLogger.verbose(LOG_TAG, "failed to insert into friends table");
        }

        return uri;
    }

    @Override
    public int removeFriend(long id) {
        int rowsDeleted = mContentResolver.delete(
                FriendsEntry.CONTENT_URI,
                FriendsEntry.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted  + " rows from friends table");

        return rowsDeleted;
    }

    @Override
    public boolean isFriend(long friendId) {
        Cursor cursor = mContentResolver.query(
                FriendsEntry.CONTENT_URI,
                null,
                FriendsEntry.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(friendId)},
                null
        );

        if (cursor != null) {
            if (cursor.getCount() == 1) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
