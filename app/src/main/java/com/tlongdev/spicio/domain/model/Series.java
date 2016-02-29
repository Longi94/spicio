package com.tlongdev.spicio.domain.model;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 * Inner Layer, Model.
 *
 * @author Long
 * @since 2016. 02. 28.
 */
public class Series {

    private int id;

    private String[] actors;

    private int AirsDayOfWeek;

    private LocalTime AirsTime;

    private String contentRating;

    private DateTime firstAired;

    private String[] genres;

    private String imdbId;

    private String netWork;

    private String overView;

    private double tvdbRating;

    private int tvdbRatingCount;

    private int runTime;

    private String name;

    private int status;

    private String bannerPath;

    private String posterPath;

    private String zapt2itId;

    private String[] aliases;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    @Day.Enum
    public int getAirsDayOfWeek() {
        return AirsDayOfWeek;
    }

    public void setAirsDayOfWeek(@Day.Enum int airsDayOfWeek) {
        AirsDayOfWeek = airsDayOfWeek;
    }

    public LocalTime getAirsTime() {
        return AirsTime;
    }

    public void setAirsTime(LocalTime airsTime) {
        AirsTime = airsTime;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public DateTime getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(DateTime firstAired) {
        this.firstAired = firstAired;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getNetWork() {
        return netWork;
    }

    public void setNetWork(String netWork) {
        this.netWork = netWork;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Status.Enum
    public int getStatus() {
        return status;
    }

    public void setStatus(@Status.Enum int status) {
        this.status = status;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getZapt2itId() {
        return zapt2itId;
    }

    public void setZapt2itId(String zapt2itId) {
        this.zapt2itId = zapt2itId;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }
}
