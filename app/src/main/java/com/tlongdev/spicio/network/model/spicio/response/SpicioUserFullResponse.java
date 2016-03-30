package com.tlongdev.spicio.network.model.spicio.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author longi
 * @since 2016.03.28.
 */
public class SpicioUserFullResponse {

    private long id;

    private String name;

    private String email;

    @SerializedName("facebook_id")
    private String facebookId;

    @SerializedName("google_id")
    private String googleId;

    private List<SpicioSeries> series;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public List<SpicioSeries> getSeries() {
        return series;
    }
}
