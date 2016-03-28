package com.tlongdev.spicio.network.model.trakt;

import com.google.gson.annotations.SerializedName;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktSearchResult {

    @SerializedName("type")
    private String type;

    @SerializedName("score")
    private double score;

    @SerializedName("show")
    private TraktSeries series;

    public String getType() {
        return type;
    }

    public double getScore() {
        return score;
    }

    public TraktSeries getSeries() {
        return series;
    }
}
