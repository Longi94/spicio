package com.tlongdev.spicio.network.model.trakt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktEpisode {

    @SerializedName("season")
    private int season;

    @SerializedName("number")
    private int number;

    @SerializedName("title")
    private String title;

    @SerializedName("ids")
    private TraktIds ids;

    @SerializedName("images")
    private TraktImages images;

    @SerializedName("number_abs")
    private Integer numberAbs;

    @SerializedName("overview")
    private String overview;

    @SerializedName("rating")
    private double rating;

    @SerializedName("votes")
    private int votes;

    @SerializedName("first_aired")
    private String firstAired;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("available_translations")
    private List<String> availableTranslations;

    public int getSeason() {
        return season;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public TraktIds getIds() {
        return ids;
    }

    public TraktImages getImages() {
        return images;
    }

    public Integer getNumberAbs() {
        return numberAbs;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getVotes() {
        return votes;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public List<String> getAvailableTranslations() {
        return availableTranslations;
    }
}
