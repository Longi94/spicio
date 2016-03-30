package com.tlongdev.spicio.domain.model;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public class UserFull {

    private User user;

    private List<Series> series;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
