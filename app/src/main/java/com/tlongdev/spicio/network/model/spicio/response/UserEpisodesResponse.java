package com.tlongdev.spicio.network.model.spicio.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author longi
 * @since 2016.04.05.
 */
public class UserEpisodesResponse {
    private Map<Integer, Long> watched = new HashMap<>();
    private Map<Integer, Long> skipped = new HashMap<>();
    private Map<Integer, Long> liked = new HashMap<>();

    public Map<Integer, Long> getWatched() {
        return watched;
    }

    public Map<Integer, Long> getSkipped() {
        return skipped;
    }

    public Map<Integer, Long> getLiked() {
        return liked;
    }
}