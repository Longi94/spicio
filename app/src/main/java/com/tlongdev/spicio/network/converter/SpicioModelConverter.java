package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserFull;
import com.tlongdev.spicio.network.model.spicio.request.SpicioSeriesBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioUserBody;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserFullResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserResponse;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Locale;

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
        userFull.setSeries(null); // TODO: 2016. 03. 30.

        userFull.setUser(user);
        return userFull;
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
}
