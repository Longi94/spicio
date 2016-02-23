package com.tlongdev.spicio.model;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.List;

/**
 * Created by Long on 2016. 02. 23..
 */
public class Series {
    private int id;
    private String name;
    private String aliases;
    private String bannerPath;
    private String overView;
    private DateTime firstAired;
    private String imdbId;
    private int zapt2itId;
    private String netWork;

    private List<String> actors;
    private int AirsDayOfWeek;
    private LocalTime AirsTime;
    private String contentRating;
    private List<String> genres;
    private double rating;
    private double ratingCuont;
    private int runTime;
    private int status;
    private String poster;

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

    public DateTime getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(DateTime firstAired) {
        this.firstAired = firstAired;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public int getZapt2itId() {
        return zapt2itId;
    }

    public void setZapt2itId(int zapt2itId) {
        this.zapt2itId = zapt2itId;
    }

    public String getNetWork() {
        return netWork;
    }

    public void setNetWork(String netWork) {
        this.netWork = netWork;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public int getAirsDayOfWeek() {
        return AirsDayOfWeek;
    }

    public void setAirsDayOfWeek(int airsDayOfWeek) {
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRatingCuont() {
        return ratingCuont;
    }

    public void setRatingCuont(double ratingCuont) {
        this.ratingCuont = ratingCuont;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
