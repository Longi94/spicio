package com.tlongdev.spicio.domain.repository.impl;

import android.content.ContentUris;
import android.net.Uri;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserFull;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.network.SpicioInterface;
import com.tlongdev.spicio.network.converter.SpicioModelConverter;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserFullResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserResponse;
import com.tlongdev.spicio.util.Logger;

import java.io.IOException;
import java.util.ArrayList;
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public List<User> searchUser(String query) {
        try {
            Call<List<SpicioUserResponse>> call = mInterface.searchUsers(query);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<List<SpicioUserResponse>> response = call.execute();

            if (response.body() != null) {
                List<User> users = new ArrayList<>();
                for (SpicioUserResponse userResponse : response.body()) {
                    users.add(SpicioModelConverter.convertToUser(userResponse));
                }
                mLogger.verbose(LOG_TAG, "call returned " + users.size() + " users");
                return users;
            } else {
                mLogger.verbose(LOG_TAG, "call returned null, code: " + response.raw().code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteUser(long id) {
        return false;
    }

    @Override
    public UserFull getUser(long id, boolean full) {
        try {
            Call<SpicioUserFullResponse> call = mInterface.getUser(id, full);

            mLogger.debug(LOG_TAG, "calling " + call.request().url().toString());
            Response<SpicioUserFullResponse> response = call.execute();

            int code = response.raw().code();
            if (code == 200) {
                if (response.body() != null) {
                    return SpicioModelConverter.convertToUserFull(response.body());
                } else {
                    mLogger.debug(LOG_TAG, "get user returned null");
                }
            } else {
                mLogger.debug(LOG_TAG, "response code: " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
