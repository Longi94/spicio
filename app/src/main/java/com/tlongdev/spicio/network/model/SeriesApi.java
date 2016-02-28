package com.tlongdev.spicio.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Outer layer, Network.
 * Represents a single series.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
@Root(name = "Series", strict = false)
public class SeriesApi {

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
    private String posterPath;

    @Element(name = "zap2it_id", required = false)
    private String zapt2itId;

    @Element(name = "AliasNames", required = false)
    private String aliases;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAliases() {
        return aliases;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public String getOverView() {
        return overView;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getZapt2itId() {
        return zapt2itId;
    }

    public String getNetWork() {
        return netWork;
    }

    public String getActors() {
        return actors;
    }

    public String getAirsDayOfWeek() {
        return AirsDayOfWeek;
    }

    public String getAirsTime() {
        return AirsTime;
    }

    public String getContentRating() {
        return contentRating;
    }

    public String getGenres() {
        return genres;
    }

    public double getTvdbRating() {
        return tvdbRating;
    }

    public int getTvdbRatingCount() {
        return tvdbRatingCount;
    }

    public int getRunTime() {
        return runTime;
    }

    public String getStatus() {
        return status;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
