package com.tlongdev.spicio.domain.model;

import org.joda.time.DateTime;

/**
 * Inner Layer, Model.
 *
 * @author Long
 * @since 2016. 02. 28.
 */
public class TvdbEpisodeOld {

    private int id;

    private int seasonId;

    private int episodeNumber;

    private String episodeName;

    private DateTime firstAired;

    private String[] guestStars;

    private String director;

    private String[] writers;

    private String overView;

    private int seasonNumber;

    private int absoluteNumber;

    private String fileName;

    private int seriesId;

    private String imdbId;

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

    public DateTime getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(DateTime firstAired) {
        this.firstAired = firstAired;
    }

    public String[] getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(String[] guestStars) {
        this.guestStars = guestStars;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getWriters() {
        return writers;
    }

    public void setWriters(String[] writers) {
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
