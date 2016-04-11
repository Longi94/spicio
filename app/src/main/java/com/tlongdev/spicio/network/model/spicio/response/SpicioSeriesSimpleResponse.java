package com.tlongdev.spicio.network.model.spicio.response;

import com.google.gson.annotations.SerializedName;

/**
 * @author longi
 * @since 2016.04.11.
 */
public class SpicioSeriesSimpleResponse {

    private String title;

    @SerializedName("trakt_id")
    private Integer traktId;

    @SerializedName("poster_thumb")
    private String posterThumb;

    private String thumb;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTraktId() {
        return traktId;
    }

    public void setTraktId(Integer traktId) {
        this.traktId = traktId;
    }

    public String getPosterThumb() {
        return posterThumb;
    }

    public void setPosterThumb(String posterThumb) {
        this.posterThumb = posterThumb;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
