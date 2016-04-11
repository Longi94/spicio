package com.tlongdev.spicio.network.model.spicio.response;

import com.tlongdev.spicio.domain.model.ActivityType;

/**
 * @author longi
 * @since 2016.04.11.
 */
public class SpicioActivityResponse {

    private int type;

    private SpicioSeriesSimpleResponse series;

    private SpicioEpisodeSimpleResponse episode;

    private SpicioUserResponse culprit;

    private SpicioUserResponse victim;

    private long timestamp;

    @ActivityType.Enum
    public int getType() {
        return type;
    }

    public void setType(@ActivityType.Enum int type) {
        this.type = type;
    }

    public SpicioSeriesSimpleResponse getSeries() {
        return series;
    }

    public void setSeries(SpicioSeriesSimpleResponse series) {
        this.series = series;
    }

    public SpicioEpisodeSimpleResponse getEpisode() {
        return episode;
    }

    public void setEpisode(SpicioEpisodeSimpleResponse episode) {
        this.episode = episode;
    }

    public SpicioUserResponse getCulprit() {
        return culprit;
    }

    public void setCulprit(SpicioUserResponse culprit) {
        this.culprit = culprit;
    }

    public SpicioUserResponse getVictim() {
        return victim;
    }

    public void setVictim(SpicioUserResponse victim) {
        this.victim = victim;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
