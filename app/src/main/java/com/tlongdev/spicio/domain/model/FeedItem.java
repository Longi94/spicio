package com.tlongdev.spicio.domain.model;

import com.tlongdev.spicio.network.model.TvdbEpisode;
import com.tlongdev.spicio.network.model.TvdbSeries;

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
    private TvdbSeries series;
    private TvdbEpisode episode;

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

    public TvdbSeries getSeries() {
        return series;
    }

    public void setSeries(TvdbSeries series) {
        this.series = series;
    }

    public TvdbEpisode getEpisode() {
        return episode;
    }

    public void setEpisode(TvdbEpisode episode) {
        this.episode = episode;
    }
}
