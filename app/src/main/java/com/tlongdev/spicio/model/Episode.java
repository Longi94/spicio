package com.tlongdev.spicio.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Represents a single episode.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
@Root(name = "Episode", strict = false)
public class Episode {

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

    public void setId(int id) {
        this.id = id;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(String guestStars) {
        this.guestStars = guestStars;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getAbsoluteNumber() {
        return absoluteNumber;
    }

    public void setAbsoluteNumber(int absoluteNumber) {
        this.absoluteNumber = absoluteNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public double getTvdbRating() {
        return tvdbRating;
    }

    public void setTvdbRating(double tvdbRating) {
        this.tvdbRating = tvdbRating;
    }
}
