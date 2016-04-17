package com.tlongdev.spicio.domain.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class UserEpisodes {
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

    public void setWatched(Map<Integer, Long> watched) {
        this.watched = watched;
    }

    public void setSkipped(Map<Integer, Long> skipped) {
        this.skipped = skipped;
    }

    public void setLiked(Map<Integer, Long> liked) {
        this.liked = liked;
    }
}
