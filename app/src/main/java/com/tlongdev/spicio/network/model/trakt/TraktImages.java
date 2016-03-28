package com.tlongdev.spicio.network.model.trakt;

import com.google.gson.annotations.SerializedName;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktImages {

    @SerializedName("fanart")
    private TraktImage fanart;

    @SerializedName("poster")
    private TraktImage poster;

    @SerializedName("logo")
    private TraktImage logo;

    @SerializedName("clearart")
    private TraktImage clearart;

    @SerializedName("banner")
    private TraktImage banner;

    @SerializedName("thumb")
    private TraktImage thumb;

    @SerializedName("screenshot")
    private TraktImage screenshot;

    public TraktImage getFanart() {
        return fanart;
    }

    public TraktImage getPoster() {
        return poster;
    }

    public TraktImage getLogo() {
        return logo;
    }

    public TraktImage getClearart() {
        return clearart;
    }

    public TraktImage getBanner() {
        return banner;
    }

    public TraktImage getThumb() {
        return thumb;
    }

    public TraktImage getScreenshot() {
        return screenshot;
    }
}
