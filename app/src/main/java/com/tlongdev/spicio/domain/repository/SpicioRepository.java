package com.tlongdev.spicio.domain.repository;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserFull;

import java.util.List;

/**
 * @author longi
 * @since 2016.03.28.
 */
public interface SpicioRepository {

    long login(User user);

    List<User> searchUser(String query);

    boolean deleteUser(long id);

    UserFull getUser(long id, boolean full);

    boolean addSeries(long userId, Series series);
}
