package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.Day;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Image;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.Status;
import com.tlongdev.spicio.network.model.TraktEpisode;
import com.tlongdev.spicio.network.model.TraktImage;
import com.tlongdev.spicio.network.model.TraktImages;
import com.tlongdev.spicio.network.model.TraktSeries;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;
import java.util.Locale;

/**
 * @author Long
 * @since 2016. 03. 02.
 */
public class TraktModelConverter {

    public static Image convertToImage(TraktImage traktImage) {
        if (traktImage == null) {
            return null;
        }

        Image image = new Image();

        image.setFull(traktImage.getFull());
        image.setMedium(traktImage.getMedium());
        image.setThumb(traktImage.getThumb());

        return image;
    }

    public static Images convertToImages(TraktImages traktImages) {
        if (traktImages == null) {
            return null;
        }

        Images images = new Images();

        images.setBanner(convertToImage(traktImages.getBanner()));
        images.setClearArt(convertToImage(traktImages.getClearart()));
        images.setFanArt(convertToImage(traktImages.getFanart()));
        images.setLogo(convertToImage(traktImages.getLogo()));
        images.setPoster(convertToImage(traktImages.getPoster()));
        images.setScreenshot(convertToImage(traktImages.getScreenshot()));
        images.setThumb(convertToImage(traktImages.getThumb()));

        return images;
    }

    public static Series convertToSeries(TraktSeries traktSeries) {
        if (traktSeries == null) {
            return null;
        }

        Series series = new Series();

        series.setCertification(traktSeries.getCertification());
        List<String> genres = traktSeries.getGenres();
        series.setGenres(genres.toArray(new String[genres.size()]));
        series.setYear(traktSeries.getYear());
        series.setOverview(traktSeries.getOverview());
        series.setTitle(traktSeries.getTitle());
        series.setRuntime(traktSeries.getRuntime());
        series.setTrailer(traktSeries.getTrailer());
        series.setTraktRating(traktSeries.getRating());
        series.setTraktRatingCount(traktSeries.getVotes());
        series.setNetwork(traktSeries.getNetwork());

        series.setImages(convertToImages(traktSeries.getImages()));

        series.setImdbId(traktSeries.getIds().getImdb());
        series.setTmdbId(traktSeries.getIds().getTmdb() == null ? -1 : traktSeries.getIds().getTmdb());
        series.setTraktId(traktSeries.getIds().getTrakt() == null ? -1 : traktSeries.getIds().getTrakt());
        series.setTvdbId(traktSeries.getIds().getTvdb() == null ? -1 : traktSeries.getIds().getTvdb());
        series.setTvRageId(traktSeries.getIds().getTvrage() == null ? -1 : traktSeries.getIds().getTvrage());
        series.setSlugName(traktSeries.getIds().getSlug());

        switch (traktSeries.getStatus()) { // TODO: 2016. 03. 05. other status codes
            case "returning series":
                series.setStatus(Status.CONTINUING);
                break;
            default:
                series.setStatus(Status.NULL);
                break;
        }

        if (traktSeries.getAirs() != null) {
            if (traktSeries.getAirs().getDay() != null) {
                switch (traktSeries.getAirs().getDay()) {
                    case "Monday":
                        series.setDayOfAiring(Day.MONDAY);
                        break;
                    case "Tuesday":
                        series.setDayOfAiring(Day.TUESDAY);
                        break;
                    case "Wednesday":
                        series.setDayOfAiring(Day.WEDNESDAY);
                        break;
                    case "Thursday":
                        series.setDayOfAiring(Day.THURSDAY);
                        break;
                    case "Friday":
                        series.setDayOfAiring(Day.FRIDAY);
                        break;
                    case "Saturday":
                        series.setDayOfAiring(Day.SATURDAY);
                        break;
                    case "Sunday":
                        series.setDayOfAiring(Day.SUNDAY);
                        break;
                    default:
                        throw new IllegalStateException(traktSeries.getAirs().getDay());
                }
            }

            if (traktSeries.getAirs().getTimezone() != null) {
                series.setAirTimeZone(DateTimeZone.forID(traktSeries.getAirs().getTimezone()));
            }

            if (traktSeries.getAirs().getTime() != null) {
                DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("H:mm").withLocale(Locale.US);
                series.setTimeOfAiring(LocalTime.parse(traktSeries.getAirs().getTime(), timeFormatter));
            }

            if (traktSeries.getFirstAired() != null) {
                DateTimeFormatter dateFormatter = ISODateTimeFormat.dateTime(); //yyyyMMdd'T'HHmmss.SSSZ
                series.setFirstAired(dateFormatter.parseDateTime(traktSeries.getFirstAired()));
            }
        }

        return series;
    }

    public static Episode convertToEpisode(TraktEpisode traktEpisode) {
        Episode episode = new Episode();

        episode.setSeason(traktEpisode.getSeason());
        episode.setNumber(traktEpisode.getNumber());
        episode.setTitle(traktEpisode.getTitle());
        episode.setImages(convertToImages(traktEpisode.getImages()));
        episode.setAbsoluteNumber(traktEpisode.getNumberAbs() == null ? -1 : traktEpisode.getNumberAbs());
        episode.setOverview(traktEpisode.getOverview());
        episode.setTraktRating(traktEpisode.getRating());
        episode.setTraktRatingCount(traktEpisode.getVotes());

        episode.setImdbId(traktEpisode.getIds().getImdb());
        episode.setTmdbId(traktEpisode.getIds().getTmdb() == null ? -1 : traktEpisode.getIds().getTmdb());
        episode.setTraktId(traktEpisode.getIds().getTrakt() == null ? -1 : traktEpisode.getIds().getTrakt());
        episode.setTvdbId(traktEpisode.getIds().getTvdb() == null ? -1 : traktEpisode.getIds().getTvdb());
        episode.setTvRageId(traktEpisode.getIds().getTvrage() == null ? -1 : traktEpisode.getIds().getTvrage());
        episode.setSlugName(traktEpisode.getIds().getSlug());

        if (traktEpisode.getFirstAired() != null) {
            DateTimeFormatter dateFormatter = ISODateTimeFormat.dateTime(); //yyyyMMdd'T'HHmmss.SSSZ
            episode.setFirstAired(dateFormatter.parseDateTime(traktEpisode.getFirstAired()));
        }

        return episode;
    }
}
