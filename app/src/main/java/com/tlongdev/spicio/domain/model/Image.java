package com.tlongdev.spicio.domain.model;

/**
 * @author Long
 * @since 2016. 03. 03.
 */
public class Image {
    private String full;

    private String medium;

    private String thumb;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
