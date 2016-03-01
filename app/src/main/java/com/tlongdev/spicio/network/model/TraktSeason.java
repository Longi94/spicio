package com.tlongdev.spicio.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktSeason {

    @SerializedName("number")
    private int number;

    @SerializedName("ids")
    private TraktIds ids;

    @SerializedName("images")
    private TraktImages images;

    @SerializedName("rating")
    private double rating;

    @SerializedName("votes")
    private int votes;

    @SerializedName("episode_count")
    private int episodeCount;

    @SerializedName("aired_episodes")
    private int airedEpisodes;

    @SerializedName("overview")
    private String overview;

    public int getNumber() {
        return number;
    }

    public TraktIds getIds() {
        return ids;
    }

    public TraktImages getImages() {
        return images;
    }

    public double getRating() {
        return rating;
    }

    public int getVotes() {
        return votes;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public int getAiredEpisodes() {
        return airedEpisodes;
    }

    public String getOverview() {
        return overview;
    }
}
