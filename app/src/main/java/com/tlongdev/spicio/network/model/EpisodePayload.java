package com.tlongdev.spicio.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * A wrapper class required for XML parsing.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
@Root(name = "Data", strict = false)
public class EpisodePayload {

    @Element(name = "Episode")
    private EpisodeApi episode;

    public EpisodeApi getEpisode() {
        return episode;
    }
}
