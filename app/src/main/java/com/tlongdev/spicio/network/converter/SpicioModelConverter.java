package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.SeriesActivities;
import com.tlongdev.spicio.domain.model.UserFull;
import com.tlongdev.spicio.network.model.spicio.request.SpicioEpisodeBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioSeriesBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioUserBody;
import com.tlongdev.spicio.network.model.spicio.response.SpicioSeriesResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserFullResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserResponse;

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
        SpicioUserBody body = new SpicioUserBody();
        body.setName(user.getName());
        body.setEmail(user.getEmailAddress());
        body.setFacebookId(user.getFacebookId());
        body.setGoogleId(user.getGooglePlusId());
        return body;
    }

    public static UserFull convertToUserFull(SpicioUserFullResponse body) {
        UserFull userFull = new UserFull();
        User user = new User();
        user.setName(body.getName());
        user.setId(body.getId());
        user.setAvatarUrl(null); // TODO: 2016. 03. 30.
        user.setEmailAddress(body.getEmail());
        user.setFacebookId(body.getFacebookId());
        user.setGooglePlusId(body.getGoogleId());

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

        userFull.setSeries(series);
        userFull.setUser(user);
        userFull.setActivities(activitiesMap);

        return userFull;
    }

    private static Series convertToSeries(SpicioSeriesResponse seriesResponse) {
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
        User user = new User();
        user.setId(userBody.getId());
        user.setName(userBody.getName());
        user.setEmailAddress(userBody.getEmail());
        user.setAvatarUrl(null);// TODO: 2016. 03. 30.
        return user;
    }

    public static SpicioSeriesBody convertToSeriesBody(Series series) {
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
        SpicioEpisodeBody body = new SpicioEpisodeBody();
        body.setTraktId(episode.getTraktId());
        body.setTitle(episode.getTitle());
        body.setNumber(episode.getNumber());
        body.setSeason(episode.getSeason());
        body.setTimestamp(System.currentTimeMillis());
        body.setThumb(episode.getImages().getThumb().getFull());
        return body;
    }
}
