package com.tlongdev.spicio.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Long on 2016. 02. 23..
 */
@Root(name = "Series", strict = false)
public class Series {

    @Element(name = "id")
    private int id;

    @Element(name = "Actors")
    private String actors;

    @Element(name = "Airs_DayOfWeek")
    private String AirsDayOfWeek;

    @Element(name = "Airs_Time")
    private String AirsTime;

    @Element(name = "ContentRating")
    private String contentRating;

    @Element(name = "FirstAired")
    private String firstAired;

    @Element(name = "Genre")
    private String genres;

    @Element(name = "IMDB_ID")
    private String imdbId;

    @Element(name = "Network")
    private String netWork;

    @Element(name = "Overview")
    private String overView;

    @Element(name = "Rating")
    private double tvdbRating;

    @Element(name = "RatingCount")
    private int tvdbRatingCount;

    @Element(name = "Runtime")
    private int runTime;

    @Element(name = "SeriesName")
    private String name;

    @Element(name = "Status")
    private String status;

    @Element(name = "banner")
    private String bannerPath;

    @Element(name = "poster")
    private String poster;

    @Element(name = "zap2it_id")
    private String zapt2itId;

    @Element(name = "AliasNames")
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
