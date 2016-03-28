package com.tlongdev.spicio.network.model.spicio.response;

/**
 * @author longi
 * @since 2016.03.28.
 */
public class SpicioUserResponse {

    private long id;

    private String name;

    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
