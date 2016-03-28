package com.tlongdev.spicio.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Inner Layer, Model.
 *
 * @author Long
 * @since 2016. 02. 25.
 */
public class User {

    private int id;

    private String name;

    @SerializedName("facebook_id")
    private String facebookId;

    @SerializedName("google_id")
    private String googlePlusId;

    @SerializedName("email")
    private String emailAddress;

    @SerializedName("avatar")
    private String avatarUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGooglePlusId() {
        return googlePlusId;
    }

    public void setGooglePlusId(String googlePlusId) {
        this.googlePlusId = googlePlusId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
