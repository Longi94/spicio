package com.tlongdev.spicio.domain.repository.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.network.SpicioInterface;

import java.util.List;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.03.28.
 */
public class SpicioRepositoryImpl implements SpicioRepository {

    @Inject SpicioInterface mInterface;

    public SpicioRepositoryImpl(SpicioApplication application) {
        application.getNetworkComponent().inject(this);
    }

    @Override
    public long login(User user) {
        return -1;
    }

    @Override
    public List<User> searchUser(String query) {
        return null;
    }

    @Override
    public boolean deleteUser(long id) {
        return false;
    }

    @Override
    public User getUser(long id, boolean full) {
        return null;
    }
}
