package com.tlongdev.spicio.domain.repository;

import com.tlongdev.spicio.domain.model.User;

import java.util.List;

/**
 * @author longi
 * @since 2016.03.28.
 */
public interface SpicioRepository {

    boolean login(User user);

    List<User> searchUser(String query);

    boolean deleteUser(long id);

    User getUser(long id, boolean full);
}
