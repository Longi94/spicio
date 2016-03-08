package com.tlongdev.spicio.domain.model;

/**
 * Inner Layer, Model.
 *
 * @author Long
 * @since 2016. 02. 29.
 */
public class Season {
    private int seriesId;
    private int number;
    private Images images;

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}
