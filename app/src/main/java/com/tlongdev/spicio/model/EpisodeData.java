package com.tlongdev.spicio.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Long on 2016. 02. 24..
 */
@Root(name = "Data", strict = false)
public class EpisodeData {

    @Element(name = "Episode")
    private Episode episode;

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
