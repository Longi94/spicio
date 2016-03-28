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

    private static String PREF_KEY_USER = "pref_user_data";

    @Inject SharedPreferences mPrefs;
    @Inject SharedPreferences.Editor mEditor;
    @Inject Gson mGson;

    private User mUser;

    public ProfileManager(SpicioApplication application) {
        application.getProfileManagerComponent().inject(this);
    }

    public boolean isLoggedIn() {
        return mPrefs.contains(PREF_KEY_USER);
    }

    public void logout() {
        mEditor.remove(PREF_KEY_USER);
        mEditor.apply();
        mUser = null;
    }

    public void login(User user) {
        mEditor.putString(PREF_KEY_USER, mGson.toJson(user));
        mEditor.apply();
        mUser = user;
    }

    public User getUser() {
        if (mUser == null) {
            mUser = mGson.fromJson(mPrefs.getString(PREF_KEY_USER, null), User.class);
        }
        return mUser;
    }

    public String getFacebookId(){
        return getUser().getFacebookId();
    }

    public String getGoogleId() {
        return getUser().getGooglePlusId();
    }
}
