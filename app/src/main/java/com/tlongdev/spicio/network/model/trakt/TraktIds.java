package com.tlongdev.spicio.network.model.trakt;

import com.google.gson.annotations.SerializedName;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktIds {

    @SerializedName("trakt")
    private Integer trakt;

    @SerializedName("slug")
    private String slug;

    @SerializedName("tvdb")
    private Integer tvdb;

    @SerializedName("imdb")
    private String imdb;

    @SerializedName("tmdb")
    private Integer tmdb;

    @SerializedName("tvrage")
    private Integer tvrage;

    public Integer getTrakt() {
        return trakt;
    }

    public String getSlug() {
        return slug;
    }

    public Integer getTvdb() {
        return tvdb;
    }

    public String getImdb() {
        return imdb;
    }

    public Integer getTmdb() {
        return tmdb;
    }

    public Integer getTvrage() {
        return tvrage;
    }
}
