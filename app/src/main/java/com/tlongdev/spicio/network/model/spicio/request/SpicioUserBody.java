package com.tlongdev.spicio.network.model.spicio.request;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author longi
 * @since 2016.03.28.
 */
public class SpicioUserBody {

    private String name;

    private String email;

    @SerializedName("facebook_id")
    private String facebookId;

    @SerializedName("google_id")
    private String googleId;

    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
