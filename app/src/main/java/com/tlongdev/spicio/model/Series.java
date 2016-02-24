package com.tlongdev.spicio.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Represents a single series.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
@Root(name = "Series", strict = false)
public class Series {

    @Element(name = "id")
    private int id;

    @Element(name = "Actors", required = false)
    private String actors;

    @Element(name = "Airs_DayOfWeek", required = false)
    private String AirsDayOfWeek;

    @Element(name = "Airs_Time", required = false)
    private String AirsTime;

    @Element(name = "ContentRating", required = false)
    private String contentRating;

    @Element(name = "FirstAired", required = false)
    private String firstAired;

    @Element(name = "Genre", required = false)
    private String genres;

    @Element(name = "IMDB_ID", required = false)
    private String imdbId;

    @Element(name = "Network", required = false)
    private String netWork;

    @Element(name = "Overview", required = false)
    private String overView;

    @Element(name = "Rating", required = false)
    private double tvdbRating;

    @Element(name = "RatingCount", required = false)
    private int tvdbRatingCount;

    @Element(name = "Runtime", required = false)
    private int runTime;

    @Element(name = "SeriesName", required = false)
    private String name;

    @Element(name = "Status", required = false)
    private String status;

    @Element(name = "banner", required = false)
    private String bannerPath;

    @Element(name = "poster", required = false)
    private String poster;

    @Element(name = "zap2it_id", required = false)
    private String zapt2itId;

    @Element(name = "AliasNames", required = false)
    private String aliases;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getZapt2itId() {
        return zapt2itId;
    }

    public void setZapt2itId(String zapt2itId) {
        this.zapt2itId = zapt2itId;
    }

    public String getNetWork() {
        return netWork;
    }

    public void setNetWork(String netWork) {
        this.netWork = netWork;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getAirsDayOfWeek() {
        return AirsDayOfWeek;
    }

    public void setAirsDayOfWeek(String airsDayOfWeek) {
        AirsDayOfWeek = airsDayOfWeek;
    }

    public String getAirsTime() {
        return AirsTime;
    }

    public void setAirsTime(String airsTime) {
        AirsTime = airsTime;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public double getTvdbRating() {
        return tvdbRating;
    }

    public void setTvdbRating(double tvdbRating) {
        this.tvdbRating = tvdbRating;
    }

    public int getTvdbRatingCount() {
        return tvdbRatingCount;
    }

    public void setTvdbRatingCount(int tvdbRatingCount) {
        this.tvdbRatingCount = tvdbRatingCount;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
