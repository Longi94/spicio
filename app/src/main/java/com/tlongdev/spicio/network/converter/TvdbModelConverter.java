package com.tlongdev.spicio.network.converter;

import com.tlongdev.spicio.domain.model.Day;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.Status;
import com.tlongdev.spicio.network.model.TvdbEpisode;
import com.tlongdev.spicio.network.model.TvdbEpisodePayload;
import com.tlongdev.spicio.network.model.TvdbSeries;
import com.tlongdev.spicio.network.model.TvdbSeriesPayload;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Middle Layer, Converter.
 *
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbModelConverter {

    /**
     * Convert a TvdbSeries object to a Series object.
     *
     * @param series the object to convert
     * @return the result object
     */
    public static Series convertToDomainModel(TvdbSeries series) {

        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("h:mm a").withLocale(Locale.US);

        Series converted = new Series();

        converted.setId(series.getId());
        if (series.getActors() != null) {
            converted.setActors(series.getActors().replaceAll("^\\|", "").split("\\|"));
        }

        if (series.getAirsDayOfWeek() != null) {
            switch (series.getAirsDayOfWeek()) {
                case "Monday":
                    converted.setAirsDayOfWeek(Day.MONDAY);
                    break;
                case "Tuesday":
                    converted.setAirsDayOfWeek(Day.TUESDAY);
                    break;
                case "Wednesday":
                    converted.setAirsDayOfWeek(Day.WEDNESDAY);
                    break;
                case "Thursday":
                    converted.setAirsDayOfWeek(Day.THURSDAY);
                    break;
                case "Friday":
                    converted.setAirsDayOfWeek(Day.FRIDAY);
                    break;
                case "Saturday":
                    converted.setAirsDayOfWeek(Day.SATURDAY);
                    break;
                case "Sunday":
                    converted.setAirsDayOfWeek(Day.SUNDAY);
                    break;
                default:
                    throw new IllegalStateException("Airs_DayOfWeek is set to something else then a day: " + series.getAirsDayOfWeek());
            }
        }

        if (series.getAirsTime() != null) {
            converted.setAirsTime(LocalTime.parse(series.getAirsTime(), timeFormatter));
        }
        converted.setContentRating(series.getContentRating());
        if (series.getFirstAired() != null) {
            converted.setFirstAired(dateFormatter.parseDateTime(series.getFirstAired()));
        }
        if (series.getGenres() != null) {
            converted.setGenres(series.getGenres().replaceAll("^\\|", "").split("\\|"));
        }
        converted.setImdbId(series.getImdbId());
        converted.setNetWork(series.getNetWork());
        converted.setOverView(series.getOverView());
        converted.setTvdbRating(series.getTvdbRating());
        converted.setTvdbRatingCount(series.getTvdbRatingCount());
        converted.setRunTime(series.getRunTime());
        converted.setName(series.getName());

        if (series.getStatus() != null) {
            switch (series.getStatus()) {
                case "Continuing":
                    converted.setStatus(Status.CONTINUING);
                    break;
                case "Ended":
                    converted.setStatus(Status.ENDED);
                    break;
                default:
                    converted.setStatus(Status.NULL);
                    break;
            }
        }

        converted.setBannerPath(series.getBannerPath());
        converted.setPosterPath(series.getPosterPath());
        converted.setZapt2itId(series.getZapt2itId());
        if (series.getAliases() != null) {
            converted.setAliases(series.getAliases().replaceAll("^\\|", "").split("\\|"));
        }

        return converted;
    }

    public static List<Series> convertToDomainModel(TvdbSeriesPayload tvdbSeriesPayload) {
        List<Series> resultList = new LinkedList<>();

        for (TvdbSeries series : tvdbSeriesPayload.getSeries()) {
            resultList.add(convertToDomainModel(series));
        }

        return resultList;
    }

    public static Episode convertToDomainModel(TvdbEpisode episode) {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        Episode converted = new Episode();

        converted.setId(episode.getId());
        converted.setSeasonId(episode.getSeasonId());
        converted.setEpisodeNumber(episode.getEpisodeNumber());
        converted.setEpisodeName(episode.getEpisodeName());
        if (episode.getFirstAired() != null) {
            converted.setFirstAired(dateFormatter.parseDateTime(episode.getFirstAired()));
        }
        if (episode.getGuestStars() != null) {
            converted.setGuestStars(episode.getGuestStars().replaceAll("^\\|", "").split("\\|"));
        }
        converted.setDirector(episode.getDirector());
        if (episode.getWriters() != null) {
            converted.setWriters(episode.getWriters().replaceAll("^\\|", "").split("\\|"));
        }
        converted.setOverView(episode.getOverView());
        converted.setSeasonNumber(episode.getSeasonNumber());
        converted.setAbsoluteNumber(episode.getAbsoluteNumber());
        converted.setFileName(episode.getFileName());
        converted.setSeriesId(episode.getSeriesId());
        converted.setImdbId(episode.getImdbId());
        converted.setTvdbRating(episode.getTvdbRating());
        return converted;
    }

    public static Episode convertToDomainModel(TvdbEpisodePayload tvdbEpisodePayload) {
        return convertToDomainModel(tvdbEpisodePayload.getEpisode());
    }
}
