package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.SeriesActivities;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.domain.model.UserEpisodes;
import com.tlongdev.spicio.domain.model.UserFull;
import com.tlongdev.spicio.network.model.spicio.request.SpicioEpisodeBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioSeriesBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioUserBody;
import com.tlongdev.spicio.network.model.spicio.response.SpicioActivityResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioEpisodeSimpleResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioSeriesResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioSeriesSimpleResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserFullResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserResponse;
import com.tlongdev.spicio.network.model.spicio.response.UserEpisodesResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Long
 * @since 2016. 03. 28.
 */
public class SpicioModelConverter {

    public static SpicioUserBody convertToUserBody(User user) {
        if (user == null) {
            return null;
        }
        SpicioUserBody body = new SpicioUserBody();
        body.setName(user.getName());
        body.setEmail(user.getEmailAddress());
        body.setFacebookId(user.getFacebookId());
        body.setGoogleId(user.getGooglePlusId());
        body.setAvatar(user.getAvatarUrl());
        return body;
    }

    public static UserFull convertToUserFull(SpicioUserFullResponse body) {
        if (body == null) {
            return null;
        }
        UserFull userFull = new UserFull();
        User user = new User();
        user.setName(body.getName());
        user.setId(body.getId());
        user.setAvatarUrl(null); // TODO: 2016. 03. 30.
        user.setEmailAddress(body.getEmail());
        user.setFacebookId(body.getFacebookId());
        user.setGooglePlusId(body.getGoogleId());
        user.setAvatarUrl(body.getAvatar());
        user.setSeriesCount(body.getSeriesCount() == null ? 0 : body.getSeriesCount());
        user.setEpisodeCount(body.getEpisodeCount() == null ? 0 : body.getEpisodeCount());

        if (body.getSeries() != null) {
            List<Series> series = new LinkedList<>();
            Map<Integer, SeriesActivities> activitiesMap = new HashMap<>();

            for (SpicioSeriesResponse seriesResponse : body.getSeries()) {
                series.add(SpicioModelConverter.convertToSeries(seriesResponse));

                if (seriesResponse.getUserEpisodes() != null) {
                    SeriesActivities activities = new SeriesActivities();
                    activities.getWatched().putAll(seriesResponse.getUserEpisodes().getWatched());
                    activities.getSkipped().putAll(seriesResponse.getUserEpisodes().getSkipped());
                    activities.getLiked().putAll(seriesResponse.getUserEpisodes().getLiked());
                    activitiesMap.put(seriesResponse.getTraktId(), activities);
                }
            }
            userFull.setActivities(activitiesMap);
            userFull.setSeries(series);
        }

        if (body.getFriends() != null) {
            List<User> friends = new LinkedList<>();

            for (SpicioUserResponse friend : body.getFriends()) {
                friends.add(SpicioModelConverter.convertToUser(friend));
            }

            userFull.setFriends(friends);
        }

        userFull.setUser(user);

        return userFull;
    }

    public static Series convertToSeries(SpicioSeriesResponse seriesResponse) {
        if (seriesResponse == null) {
            return null;
        }

        Series series = new Series();

        series.setCertification(seriesResponse.getCertification());
        series.setDayOfAiring(seriesResponse.getDayOfAiring());
        series.setImdbId(seriesResponse.getImdbId());
        series.setNetwork(seriesResponse.getNetwork());
        series.setOverview(seriesResponse.getOverview());
        series.setRuntime(seriesResponse.getRuntime());
        series.setSlugName(seriesResponse.getSlugName());
        series.setStatus(seriesResponse.getStatus());
        series.setTitle(seriesResponse.getTitle());
        series.setTmdbId(seriesResponse.getTmdbId());
        series.setTrailer(seriesResponse.getTrailer());
        series.setTraktId(seriesResponse.getTraktId());
        series.setTraktRating(seriesResponse.getTraktRating());
        series.setTraktRatingCount(seriesResponse.getTraktRatingCount());
        series.setTvdbId(seriesResponse.getTvdbId());
        series.setTvRageId(seriesResponse.getTvRageId());
        series.setYear(seriesResponse.getYear());

        Images images = new Images();
        images.setPoster(new Image());
        images.getPoster().setFull(seriesResponse.getPosterFull());
        images.getPoster().setThumb(seriesResponse.getPosterThumb());

        images.setThumb(new Image());
        images.getThumb().setFull(seriesResponse.getThumb());

        series.setImages(images);

        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("H:mm").withLocale(Locale.US);
        series.setTimeOfAiring(LocalTime.parse(seriesResponse.getTimeOfAiring(), timeFormatter));
        series.setFirstAired(new DateTime(seriesResponse.getFirstAired()));
        series.setAirTimeZone(DateTimeZone.forID(seriesResponse.getAirTimeZone()));

        List<String> genres = seriesResponse.getGenres();
        series.setGenres(genres.toArray(new String[genres.size()]));
        return series;
    }

    public static User convertToUser(SpicioUserResponse userBody) {
        if (userBody == null) {
            return null;
        }

        User user = new User();
        user.setId(userBody.getId());
        user.setName(userBody.getName());
        user.setEmailAddress(userBody.getEmail());
        user.setAvatarUrl(userBody.getAvatar());
        user.setSeriesCount(userBody.getSeriesCount() == null ? 0 : userBody.getSeriesCount());
        user.setEpisodeCount(userBody.getEpisodeCount() == null ? 0 : userBody.getEpisodeCount());
        return user;
    }

    public static SpicioSeriesBody convertToSeriesBody(Series series) {
        if (series == null) {
            return null;
        }

        SpicioSeriesBody body = new SpicioSeriesBody();
        body.setCertification(series.getCertification());
        body.setDayOfAiring(series.getDayOfAiring());
        body.setImdbId(series.getImdbId());
        body.setNetwork(series.getNetwork());
        body.setOverview(series.getOverview());
        body.setRuntime(series.getRuntime());
        body.setSlugName(series.getSlugName());
        body.setStatus(series.getStatus());
        body.setTitle(series.getTitle());
        body.setTmdbId(series.getTmdbId());
        body.setTrailer(series.getTrailer());
        body.setTraktId(series.getTraktId());
        body.setTraktRating(series.getTraktRating());
        body.setTraktRatingCount(series.getTraktRatingCount());
        body.setTvdbId(series.getTvdbId());
        body.setTvRageId(series.getTvRageId());
        body.setYear(series.getYear());

        body.setPosterFull(series.getImages().getPoster().getFull());
        body.setPosterThumb(series.getImages().getPoster().getThumb());
        body.setThumb(series.getImages().getThumb().getFull());

        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("H:mm").withLocale(Locale.US);
        body.setTimeOfAiring(timeFormatter.print(series.getTimeOfAiring()));
        body.setFirstAired(series.getFirstAired().getMillis());
        body.setAirTimeZone(series.getAirTimeZone().getID());
        body.setGenres(Arrays.asList(series.getGenres()));
        return body;
    }

    public static SpicioEpisodeBody convertToEpisodeBody(Episode episode) {
        if (episode == null) {
            return null;
        }

        SpicioEpisodeBody body = new SpicioEpisodeBody();
        body.setTraktId(episode.getTraktId());
        body.setTitle(episode.getTitle());
        body.setNumber(episode.getNumber());
        body.setSeason(episode.getSeason());
        body.setTimestamp(System.currentTimeMillis());
        body.setThumb(episode.getImages().getThumb().getFull());
        return body;
    }

    public static UserActivity convertToUserActivity(SpicioActivityResponse response) {
        if (response == null) {
            return null;
        }

        UserActivity activity = new UserActivity();
        activity.setCulprit(convertToUser(response.getCulprit()));
        activity.setVictim(convertToUser(response.getVictim()));
        activity.setEpisode(convertToEpisode(response.getEpisode()));
        activity.setSeries(convertToSeries(response.getSeries()));
        activity.setTimestamp(response.getTimestamp());
        activity.setType(response.getType());
        return activity;
    }

    private static Series convertToSeries(SpicioSeriesSimpleResponse response) {
        if (response == null) {
            return null;
        }

        Series series = new Series();
        series.setTitle(response.getTitle());
        series.setTraktId(response.getTraktId());
        series.setImages(new Images());
        series.getImages().setPoster(new Image());
        series.getImages().setThumb(new Image());
        series.getImages().getPoster().setThumb(response.getPosterThumb());
        series.getImages().getThumb().setFull(response.getThumb());
        return series;
    }

    private static Episode convertToEpisode(SpicioEpisodeSimpleResponse response) {
        if (response == null) {
            return null;
        }

        Episode episode = new Episode();
        episode.setTraktId(response.getTraktId());
        episode.setNumber(response.getNumber());
        episode.setSeason(response.getSeason());
        episode.setTitle(response.getTitle());
        episode.setImages(new Images());
        episode.getImages().setThumb(new Image());
        episode.getImages().getThumb().setFull(response.getThumb());
        return episode;
    }

    public static UserEpisodes convertToUserEpisodes(UserEpisodesResponse response) {
        if (response == null) {
            return null;
        }

        UserEpisodes episodes = new UserEpisodes();
        episodes.setLiked(response.getLiked());
        episodes.setSkipped(response.getSkipped());
        episodes.setWatched(response.getWatched());
        return episodes;
    }
}
