package com.tlongdev.spicio.domain.repository.impl;

import android.content.ContentUris;
import android.net.Uri;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.domain.model.UserEpisodes;
import com.tlongdev.spicio.domain.model.UserFull;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.network.SpicioInterface;
import com.tlongdev.spicio.network.converter.SpicioModelConverter;
import com.tlongdev.spicio.network.model.spicio.request.SpicioEpisodeBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioSeriesBody;
import com.tlongdev.spicio.network.model.spicio.response.SpicioActivityResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioSeriesResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserFullResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserResponse;
import com.tlongdev.spicio.network.model.spicio.response.UserEpisodesResponse;
import com.tlongdev.spicio.util.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author longi
 * @since 2016.03.28.
 */
public class SpicioRepositoryImpl implements SpicioRepository {

    private static final String LOG_TAG = SpicioRepositoryImpl.class.getSimpleName();

    @Inject SpicioInterface mInterface;
    @Inject Logger mLogger;

    private Comparator<UserActivity> sortByTime = new Comparator<UserActivity>() {
        @Override
        public int compare(UserActivity lhs, UserActivity rhs) {
            // TODO: 2016.04.15.
            return 0;
        }
    };

    public SpicioRepositoryImpl(SpicioApplication application) {
        application.getNetworkComponent().inject(this);
    }

    @Override
    public long login(User user) {
        try {
            Call<Void> call = mInterface.login(SpicioModelConverter.convertToUserBody(user));

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 201) {
                String location = response.headers().get("Location");
                mLogger.debug(LOG_TAG, location);
                return ContentUris.parseId(Uri.parse(location));
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public List<User> searchUser(String query) {
        try {
            Call<List<SpicioUserResponse>> call = mInterface.searchUsers(query);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<SpicioUserResponse>> response = call.execute();

            if (response.body() != null) {
                List<User> users = new ArrayList<>();
                for (SpicioUserResponse userResponse : response.body()) {
                    users.add(SpicioModelConverter.convertToUser(userResponse));
                }
                mLogger.verbose(LOG_TAG, "call returned " + users.size() + " users");
                return users;
            } else {
                mLogger.verbose(LOG_TAG, "call returned null, code: " + response.raw().code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteUser(long id) {
        return false;
    }

    @Override
    public UserFull getUser(long id, boolean full) {
        try {
            Call<SpicioUserFullResponse> call = mInterface.getUser(id, full);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<SpicioUserFullResponse> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                if (response.body() != null) {
                    return SpicioModelConverter.convertToUserFull(response.body());
                } else {
                    mLogger.debug(LOG_TAG, "get user returned null");
                }
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addSeries(long userId, Series series) {
        try {
            SpicioSeriesBody body = SpicioModelConverter.convertToSeriesBody(series);
            Call<Void> call = mInterface.addSeries(userId, body);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                return true;
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkEpisode(long userId, int seriesId, Episode episode, boolean checked) {
        try {
            SpicioEpisodeBody body = SpicioModelConverter.convertToEpisodeBody(episode);

            Call<Void> call = checked ? mInterface.checkEpisode(userId, seriesId, body) :
                    mInterface.unCheckEpisode(userId, seriesId, episode.getTraktId());

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                return true;
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean skipEpisode(long userId, int seriesId, Episode episode, boolean skipped) {
        try {
            SpicioEpisodeBody body = SpicioModelConverter.convertToEpisodeBody(episode);

            Call<Void> call = skipped ? mInterface.skipEpisode(userId, seriesId, body) :
                    mInterface.unSkipEpisode(userId, seriesId, episode.getTraktId());

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                return true;
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean likeEpisode(long userId, int seriesId, Episode episode, boolean liked) {
        try {
            SpicioEpisodeBody body = SpicioModelConverter.convertToEpisodeBody(episode);

            Call<Void> call = liked ? mInterface.likeEpisode(userId, seriesId, body) :
                    mInterface.unLikeEpisode(userId, seriesId, episode.getTraktId());

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                return true;
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addFriend(long userId, long friendId) {
        try {
            Call<Void> call = mInterface.addFriends(userId, friendId);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                return true;
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeFriend(long userId, long friendId) {
        try {
            Call<Void> call = mInterface.removeFriend(userId, friendId);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                return true;
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeSeries(long userId, int seriesId) {
        try {
            Call<Void> call = mInterface.removeSeries(userId, seriesId);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                return true;
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<UserActivity> getHistory(long userId) {
        try {
            Call<List<SpicioActivityResponse>> call = mInterface.getHistory(userId);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<SpicioActivityResponse>> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                if (response.body() != null) {
                    List<UserActivity> activities = new ArrayList<>();

                    for (SpicioActivityResponse activityResponse : response.body()){
                        activities.add(SpicioModelConverter.convertToUserActivity(activityResponse));
                    }

                    Collections.sort(activities, sortByTime);

                    return activities;
                } else {
                    mLogger.debug(LOG_TAG, "get user returned null");
                }
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserActivity> getFeed(long userId) {
        try {
            Call<List<SpicioActivityResponse>> call = mInterface.getFeed(userId);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<SpicioActivityResponse>> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                if (response.body() != null) {
                    List<UserActivity> activities = new ArrayList<>();

                    for (SpicioActivityResponse activityResponse : response.body()){
                        activities.add(SpicioModelConverter.convertToUserActivity(activityResponse));
                    }

                    Collections.sort(activities, sortByTime);

                    return activities;
                } else {
                    mLogger.debug(LOG_TAG, "get user returned null");
                }
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getFriends(long userId) {
        try {
            Call<List<SpicioUserResponse>> call = mInterface.getFriends(userId);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<SpicioUserResponse>> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                if (response.body() != null) {
                    List<User> friends = new ArrayList<>();

                    for (SpicioUserResponse userResponse : response.body()){
                        friends.add(SpicioModelConverter.convertToUser(userResponse));
                    }

                    return friends;
                } else {
                    mLogger.debug(LOG_TAG, "get user returned null");
                }
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Series> getSeries(long userId) {
        try {
            Call<List<SpicioSeriesResponse>> call = mInterface.getSeries(userId);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<SpicioSeriesResponse>> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                if (response.body() != null) {
                    List<Series> series = new ArrayList<>();

                    for (SpicioSeriesResponse seriesResponse : response.body()){
                        series.add(SpicioModelConverter.convertToSeries(seriesResponse));
                    }

                    return series;
                } else {
                    mLogger.debug(LOG_TAG, "get user returned null");
                }
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserEpisodes getEpisodes(long userId, int seriesId) {
        try {
            Call<UserEpisodesResponse> call = mInterface.getEpisodes(userId, seriesId);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<UserEpisodesResponse> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                if (response.body() != null) {
                    return SpicioModelConverter.convertToUserEpisodes(response.body());
                } else {
                    mLogger.debug(LOG_TAG, "get user returned null");
                }
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
