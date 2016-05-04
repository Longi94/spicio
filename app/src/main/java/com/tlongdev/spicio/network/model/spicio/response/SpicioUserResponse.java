package com.tlongdev.spicio.network.model.spicio.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author longi
 * @since 2016.03.28.
 */
public class SpicioUserResponse {

    private long id;

    private String name;

    private String email;

    private String avatar;

    @SerializedName("series_count")
    private Integer seriesCount;

    @SerializedName("episode_count")
    private Integer episodeCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(Integer seriesCount) {
        this.seriesCount = seriesCount;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }
}
