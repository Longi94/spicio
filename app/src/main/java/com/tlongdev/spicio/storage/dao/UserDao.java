package com.tlongdev.spicio.storage.dao;

import android.net.Uri;

import com.tlongdev.spicio.domain.model.SeriesActivities;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;

import java.util.List;
import java.util.Map;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public interface UserDao {
    int insertUserActivities(Map<Integer, SeriesActivities> user);

    Uri addFriend(User friend);

    int removeFriend(long id);

    List<User> getFriends();

    boolean isFriend(long friendId);

    List<UserActivity> getFeed();

    int insertFeed(List<UserActivity> activities);

    int addFriends(List<User> friends);
}
