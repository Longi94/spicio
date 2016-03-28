package com.tlongdev.spicio.network.model.trakt;

import com.google.gson.annotations.SerializedName;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktImage {

    @SerializedName("full")
    private String full;

    @SerializedName("medium")
    private String medium;

    @SerializedName("thumb")
    private String thumb;

    public String getFull() {
        return full;
    }

    public String getMedium() {
        return medium;
    }

    public String getThumb() {
        return thumb;
    }
}
