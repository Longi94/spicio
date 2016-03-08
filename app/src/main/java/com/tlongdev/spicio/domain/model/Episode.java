package com.tlongdev.spicio.domain.model;

import org.joda.time.DateTime;

/**
 * @author Long
 * @since 2016. 03. 03.
 */
public class Episode {

    private int season;

    private int number;

    private String title;

    private int traktId;

    private String slugName;

    private int tvdbId;

    private String imdbId;

    private int tmdbId;

    private int tvRageId;

    private Images images;

    private int absoluteNumber;

    private String overview;

    private double traktRating;

    private int traktRatingCount;

    private DateTime firstAired;

    private boolean watched;

    private boolean liked;

    private boolean skipped;

    public int seriesId;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTraktId() {
        return traktId;
    }

    public void setTraktId(int traktId) {
        this.traktId = traktId;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public int getTvdbId() {
        return tvdbId;
    }

    public void setTvdbId(int tvdbId) {
        this.tvdbId = tvdbId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public int getTvRageId() {
        return tvRageId;
    }

    public void setTvRageId(int tvRageId) {
        this.tvRageId = tvRageId;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public int getAbsoluteNumber() {
        return absoluteNumber;
    }

    public void setAbsoluteNumber(int absoluteNumber) {
        this.absoluteNumber = absoluteNumber;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getTraktRating() {
        return traktRating;
    }

    public void setTraktRating(double traktRating) {
        this.traktRating = traktRating;
    }

    public int getTraktRatingCount() {
        return traktRatingCount;
    }

    public void setTraktRatingCount(int traktRatingCount) {
        this.traktRatingCount = traktRatingCount;
    }

    public DateTime getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(DateTime firstAired) {
        this.firstAired = firstAired;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }
}
