package com.tlongdev.spicio.network.model.tvdb;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Outer layer, Network.
 * A wrapper class required for XML parsing.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
@Root(name = "Data", strict = false)
public class TvdbEpisodePayload {

    @Element(name = "Episode")
    private TvdbEpisode episode;

    public TvdbEpisode getEpisode() {
        return episode;
    }
}
