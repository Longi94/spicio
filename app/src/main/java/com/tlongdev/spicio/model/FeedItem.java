package com.tlongdev.spicio.model;

/**
 * @author Long
 * @since 2016. 02. 26.
 */
public class FeedItem {

    private int id;
    private int type;
    private long timestamp;
    private User culprit;
    private User victim;
    private Series series;
    private Episode episode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public User getCulprit() {
        return culprit;
    }

    public void setCulprit(User culprit) {
        this.culprit = culprit;
    }

    public User getVictim() {
        return victim;
    }

    public void setVictim(User victim) {
        this.victim = victim;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
