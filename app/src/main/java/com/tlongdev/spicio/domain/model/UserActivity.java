package com.tlongdev.spicio.domain.model;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class UserActivity {

    private int type;

    private Series series;

    private Episode episode;

    private User culprit;

    private User victim;

    private long timestamp;

    @ActivityType.Enum
    public int getType() {
        return type;
    }

    public void setType(@ActivityType.Enum int type) {
        this.type = type;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
