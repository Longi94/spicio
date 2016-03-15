package com.tlongdev.spicio.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Outer layer, Network.
 * Represents a single episode.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
@Root(name = "Episode", strict = false)
public class TvdbEpisode {

    @Element(name = "id")
    private int id;

    @Element(name = "seasonid")
    private int seasonId;

    @Element(name = "EpisodeNumber")
    private int episodeNumber;

    @Element(name = "EpisodeName")
    private String episodeName;

    @Element(name = "FirstAired")
    private String firstAired;

    @Element(name = "GuestStars")
    private String guestStars;

    @Element(name = "Director")
    private String director;

    @Element(name = "Writer")
    private String writers;

    @Element(name = "Overview")
    private String overView;

    @Element(name = "SeasonNumber")
    private int seasonNumber;

    @Element(name = "absolute_number")
    private int absoluteNumber;

    @Element(name = "filename")
    private String fileName;

    @Element(name = "seriesid")
    private int seriesId;

    @Element(name = "IMDB_ID")
    private String imdbId;

    @Element(name = "Rating")
    private double tvdbRating;

    public int getId() {
        return id;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public String getGuestStars() {
        return guestStars;
    }

    public String getDirector() {
        return director;
    }

    public String getWriters() {
        return writers;
    }

    public String getOverView() {
        return overView;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getAbsoluteNumber() {
        return absoluteNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public double getTvdbRating() {
        return tvdbRating;
    }
}
