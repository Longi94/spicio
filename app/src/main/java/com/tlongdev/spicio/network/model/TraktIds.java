package com.tlongdev.spicio.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktIds {

    @SerializedName("trakt")
    private int trakt;

    @SerializedName("slug")
    private String slug;

    @SerializedName("tvdb")
    private int tvdb;

    @SerializedName("imdb")
    private String imdb;

    @SerializedName("tmdb")
    private int tmdb;

    @SerializedName("tvrage")
    private int tvrage;

    public int getTrakt() {
        return trakt;
    }

    public String getSlug() {
        return slug;
    }

    public int getTvdb() {
        return tvdb;
    }

    public String getImdb() {
        return imdb;
    }

    public int getTmdb() {
        return tmdb;
    }

    public int getTvrage() {
        return tvrage;
    }
}
