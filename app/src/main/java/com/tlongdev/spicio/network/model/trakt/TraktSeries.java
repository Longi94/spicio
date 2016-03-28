package com.tlongdev.spicio.network.model.trakt;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktSeries {

    @SerializedName("title")
    private String title;

    @SerializedName("year")
    private int year;

    @SerializedName("ids")
    private TraktIds ids;

    @SerializedName("overview")
    private String overview;

    @SerializedName("first_aired")
    private String firstAired;

    @SerializedName("airs")
    private TraktAirTime airs;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("certification")
    private String certification;

    @SerializedName("network")
    private String network;

    @SerializedName("country")
    private String country;

    @SerializedName("trailer")
    private String trailer;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("status")
    private String status;

    @SerializedName("rating")
    private double rating;

    @SerializedName("votes")
    private int votes;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("language")
    private String language;

    @SerializedName("available_translations")
    private List<String> availableTranslations = new ArrayList<String>();

    @SerializedName("genres")
    private List<String> genres = new ArrayList<String>();

    @SerializedName("aired_episodes")
    private int airedEpisodes;

    @SerializedName("images")
    private TraktImages images;

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public TraktIds getIds() {
        return ids;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public TraktAirTime getAirs() {
        return airs;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getCertification() {
        return certification;
    }

    public String getNetwork() {
        return network;
    }

    public String getCountry() {
        return country;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getStatus() {
        return status;
    }

    public double getRating() {
        return rating;
    }

    public int getVotes() {
        return votes;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getLanguage() {
        return language;
    }

    public List<String> getAvailableTranslations() {
        return availableTranslations;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getAiredEpisodes() {
        return airedEpisodes;
    }

    public TraktImages getImages() {
        return images;
    }
}
