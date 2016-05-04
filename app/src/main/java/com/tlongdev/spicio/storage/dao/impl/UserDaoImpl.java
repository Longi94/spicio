package com.tlongdev.spicio.storage.dao.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.ActivityType;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.SeriesActivities;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.storage.DatabaseContract.ActivityEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FeedEntry;
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

    @Override
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
                user.setAvatarUrl(cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_AVATAR)));

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
        values.put(FriendsEntry.COLUMN_AVATAR, friend.getAvatarUrl());

        Uri uri = mContentResolver.insert(FriendsEntry.CONTENT_URI, values);

        if (uri != null) {
            mLogger.verbose(LOG_TAG, "inserted 1 row into friends table");
        } else {
            mLogger.verbose(LOG_TAG, "failed to insert into friends table");
        }

        return uri;
    }

    @Override
    public int addFriends(List<User> friends) {

        Vector<ContentValues> cVVector = new Vector<>();

        for (User friend : friends) {
            ContentValues values = new ContentValues();

            values.put(FriendsEntry.COLUMN_USER_ID, friend.getId());
            values.put(FriendsEntry.COLUMN_NAME, friend.getName());
            values.put(FriendsEntry.COLUMN_AVATAR, friend.getAvatarUrl());

            cVVector.add(values);
        }

        int rowsInserted = 0;

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            //Insert all the data into the database
            rowsInserted = mContentResolver.bulkInsert(FriendsEntry.CONTENT_URI, cvArray);
        }

        mLogger.verbose(LOG_TAG, "inserted " + rowsInserted + " rows into friends table");

        return rowsInserted;
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

    @Override
    public List<UserActivity> getFeed() {
        Cursor cursor = mContentResolver.query(
                FeedEntry.CONTENT_URI,
                null,
                null,
                null,
                FeedEntry.COLUMN_TIMESTAMP + " DESC"
        );

        List<UserActivity> activities = new LinkedList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                activities.add(mapCursorToUserActivity(cursor));
            }
            cursor.close();
        }
        return activities;
    }

    @Override
    public int insertFeed(List<UserActivity> activities) {
        int rowsDeleted = mContentResolver.delete(FeedEntry.CONTENT_URI, null, null);

        mLogger.verbose(LOG_TAG, "deleted " + rowsDeleted + " rows from feed table");

        Vector<ContentValues> cVVector = new Vector<>();

        for (UserActivity activity : activities) {
            cVVector.add(createContentValues(activity));
        }

        int rowsInserted = 0;

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            //Insert all the data into the database
            rowsInserted = mContentResolver.bulkInsert(FeedEntry.CONTENT_URI, cvArray);
        }

        mLogger.verbose(LOG_TAG, "inserted " + rowsInserted + " rows into feed table");

        return rowsInserted;
    }

    private ContentValues createContentValues(UserActivity activity) {
        ContentValues values = new ContentValues();

        //values.put(FeedEntry.COLUMN_ITEM_ID, activity.getId());
        values.put(FeedEntry.COLUMN_TYPE, activity.getType());
        values.put(FeedEntry.COLUMN_TIMESTAMP, activity.getTimestamp());

        if (activity.getCulprit() != null) {
            values.put(FeedEntry.COLUMN_CULPRIT_ID, activity.getCulprit().getId());
            values.put(FeedEntry.COLUMN_CULPRIT_NAME, activity.getCulprit().getName());
            values.put(FeedEntry.COLUMN_CULPRIT_IMAGE, activity.getCulprit().getAvatarUrl());
        }

        if (activity.getVictim() != null) {
            values.put(FeedEntry.COLUMN_VICTIM_ID, activity.getVictim().getId());
            values.put(FeedEntry.COLUMN_VICTIM_NAME, activity.getVictim().getName());
            values.put(FeedEntry.COLUMN_VICTIM_IMAGE, activity.getVictim().getAvatarUrl());
        }

        if (activity.getSeries() != null) {
            values.put(FeedEntry.COLUMN_SERIES_ID, activity.getSeries().getTraktId());
            values.put(FeedEntry.COLUMN_SERIES_NAME, activity.getSeries().getTitle());
            values.put(FeedEntry.COLUMN_SERIES_IMAGE, activity.getSeries().getImages().getThumb().getFull());
        }

        if (activity.getEpisode() != null) {
            values.put(FeedEntry.COLUMN_EPISODE_ID, activity.getEpisode().getTraktId());
            values.put(FeedEntry.COLUMN_EPISODE_NAME, activity.getEpisode().getTitle());
            values.put(FeedEntry.COLUMN_EPISODE_NUMBER, activity.getEpisode().getNumber());
            values.put(FeedEntry.COLUMN_EPISODE_IMAGE, activity.getEpisode().getImages().getThumb().getFull());
            values.put(FeedEntry.COLUMN_EPISODE_ABSOLUTE_NUMBER, activity.getEpisode().getAbsoluteNumber());
            values.put(FeedEntry.COLUMN_SEASON_NUMBER, activity.getEpisode().getSeason());
        }

        return values;
    }

    @SuppressWarnings("WrongConstant")
    private UserActivity mapCursorToUserActivity(Cursor cursor) {
        UserActivity activity = new UserActivity();

        activity.setType(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_TYPE)));
        activity.setTimestamp(cursor.getLong(cursor.getColumnIndex(FeedEntry.COLUMN_TIMESTAMP)));

        Series series = new Series();
        series.setTraktId(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_SERIES_ID)));
        series.setTitle(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_SERIES_NAME)));
        series.setImages(new Images());
        series.getImages().setThumb(new Image());
        series.getImages().getThumb().setFull(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_SERIES_IMAGE)));
        activity.setSeries(series);

        Episode episode = new Episode();
        episode.setTraktId(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_ID)));
        episode.setNumber(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_NUMBER)));
        episode.setSeason(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_SEASON_NUMBER)));
        episode.setTitle(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_NAME)));
        episode.setImages(new Images());
        episode.getImages().setThumb(new Image());
        episode.getImages().getThumb().setFull(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_EPISODE_IMAGE)));
        activity.setEpisode(episode);

        User culprit = new User();
        culprit.setId(cursor.getLong(cursor.getColumnIndex(FeedEntry.COLUMN_CULPRIT_ID)));
        culprit.setName(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_CULPRIT_NAME)));
        culprit.setAvatarUrl(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_CULPRIT_IMAGE)));
        activity.setCulprit(culprit);

        User victim = new User();
        victim.setId(cursor.getLong(cursor.getColumnIndex(FeedEntry.COLUMN_VICTIM_ID)));
        victim.setName(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_VICTIM_NAME)));
        victim.setAvatarUrl(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_VICTIM_IMAGE)));
        activity.setVictim(victim);
        return activity;
    }
}
