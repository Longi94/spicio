package com.tlongdev.spicio.model;

import com.tlongdev.spicio.network.model.EpisodeApi;
import com.tlongdev.spicio.network.model.SeriesApi;

/**
 * Inner Layer, Model.
 *
 * @author Long
 * @since 2016. 02. 26.
 */
public class FeedItem {

    private int id;
    private int type;
    private long timestamp;
    private User culprit;
    private User victim;
    private SeriesApi series;
    private EpisodeApi episode;

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

    public SeriesApi getSeries() {
        return series;
    }

    public void setSeries(SeriesApi series) {
        this.series = series;
    }

    public EpisodeApi getEpisode() {
        return episode;
    }

    public void setEpisode(EpisodeApi episode) {
        this.episode = episode;
    }
}
