package com.tlongdev.spicio.domain.repository.impl;

import android.content.ContentUris;
import android.net.Uri;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.network.SpicioInterface;
import com.tlongdev.spicio.network.converter.SpicioModelConverter;
import com.tlongdev.spicio.util.Logger;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author longi
 * @since 2016.03.28.
 */
public class SpicioRepositoryImpl implements SpicioRepository {

    private static final String LOG_TAG = SpicioRepositoryImpl.class.getSimpleName();

    @Inject SpicioInterface mInterface;
    @Inject Logger mLogger;

    public SpicioRepositoryImpl(SpicioApplication application) {
        application.getNetworkComponent().inject(this);
    }

    @Override
    public long login(User user) {
        try {
            Call<Void> call = mInterface.login(SpicioModelConverter.convertToUserBody(user));

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<Void> response = call.execute();

            int code = response.raw().code();
            if (code == 201) {
                String location = response.headers().get("Location");
                mLogger.debug(LOG_TAG, location);
                return ContentUris.parseId(Uri.parse(location));
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
                return -1L;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
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
