package com.tlongdev.spicio.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.User;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
public class ProfileManager {

    private static final String LOG_TAG = ProfileManager.class.getSimpleName();

    private static String PREF_KEY_USER = "pref_user_data";

    @Inject SharedPreferences mPrefs;
    @Inject SharedPreferences.Editor mEditor;
    @Inject Gson mGson;
    @Inject Logger mLogger;

    private User mUser;

    public ProfileManager(SpicioApplication application) {
        application.getProfileManagerComponent().inject(this);
    }

    public boolean isLoggedIn() {
        return mPrefs.contains(PREF_KEY_USER);
    }

    public void logout() {
        mLogger.debug(LOG_TAG, "Deleting user.");
        mEditor.remove(PREF_KEY_USER);
        mEditor.apply();
        mUser = null;
    }

    public void login(User user) {
        String json = mGson.toJson(user);

        mLogger.debug(LOG_TAG, "Saving user " + json);

        mEditor.putString(PREF_KEY_USER, json);
        mEditor.apply();
        mUser = user;
    }

    public User getUser() {
        if (mUser == null) {
            String json = mPrefs.getString(PREF_KEY_USER, null);

            if (json == null) {
                mLogger.debug(LOG_TAG, "User is null");
                return null;
            }

            mLogger.debug(LOG_TAG, "Reading and caching user: " + json);
            mUser = mGson.fromJson(json, User.class);
        } else {
            mLogger.debug(LOG_TAG, "Returning cached user.");
        }
        return mUser;
    }

    public String getFacebookId(){
        if (getUser() == null) {
            return null;
        }
        return getUser().getFacebookId();
    }

    public String getGoogleId() {
        if (getUser() == null) {
            return null;
        }
        return getUser().getGooglePlusId();
    }
}
