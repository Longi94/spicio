package com.tlongdev.spicio.network.model.spicio.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public class SpicioEpisodeBody {

    @SerializedName("trakt_id")
    private int traktId;

    private Integer number;

    private Integer season;

    private String title;

    private String thumb;

    private Long timestamp;

    private boolean watched;

    private boolean skipped;

    private boolean liked;

    public Integer getSeason() {
        return season;
    }

    public Integer getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTraktId() {
        return traktId;
    }

    public Boolean isWatched() {
        return watched;
    }

    public Boolean isLiked() {
        return liked;
    }

    public Boolean isSkipped() {
        return skipped;
    }

    public String getThumb() {
        return thumb;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTraktId(int traktId) {
        this.traktId = traktId;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
