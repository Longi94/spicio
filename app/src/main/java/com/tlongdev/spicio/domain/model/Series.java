package com.tlongdev.spicio.domain.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 03.
 */
public class Series {

    private String title;

    private int year;

    private int traktId;

    private String slugName;

    private int tvdbId;

    private String imdbId;

    private int tmdbId;

    private int tvRageId;

    private String overview;

    private DateTime firstAired;

    private int dayOfAiring;

    private LocalTime timeOfAiring;

    private DateTimeZone airTimeZone;

    private int runtime;

    private String certification;

    private String network;

    private String trailer;

    private int status;

    private double traktRating;

    private int traktRatingCount;

    private List<String> genres;

    private Images images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public DateTime getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(DateTime firstAired) {
        this.firstAired = firstAired;
    }

    @Day.Enum
    public int getDayOfAiring() {
        return dayOfAiring;
    }

    public void setDayOfAiring(@Day.Enum int dayOfAiring) {
        this.dayOfAiring = dayOfAiring;
    }

    public LocalTime getTimeOfAiring() {
        return timeOfAiring;
    }

    public void setTimeOfAiring(LocalTime timeOfAiring) {
        this.timeOfAiring = timeOfAiring;
    }

    public DateTimeZone getAirTimeZone() {
        return airTimeZone;
    }

    public void setAirTimeZone(DateTimeZone airTimeZone) {
        this.airTimeZone = airTimeZone;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Status.Enum
    public int getStatus() {
        return status;
    }

    public void setStatus(@Status.Enum int status) {
        this.status = status;
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}
