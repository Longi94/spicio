package com.tlongdev.spicio.domain.repository;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.domain.model.UserFull;

import java.util.List;

/**
 * @author longi
 * @since 2016.03.28.
 */
public interface SpicioRepository {

    long login(User user);

    List<User> searchUser(String query);

    boolean deleteUser(long id);

    UserFull getUser(long id, boolean full);

    boolean addSeries(long userId, Series series);

    boolean checkEpisode(long userId, int seriesId, Episode episode, boolean checked);

    boolean skipEpisode(long userId, int seriesId, Episode episode, boolean skipped);

    boolean likeEpisode(long userId, int seriesId, Episode episode, boolean liked);

    boolean addFriend(long userId, long friendId);

    boolean removeFriend(long userId, long friendId);

    boolean removeSeries(long userId, int seriesId);

    List<UserActivity> getHistory(long userId);

    List<UserActivity> getFeed(long userId);

    List<User> getFriends(long userId);

    List<Series> getSeries(long userId);
}
