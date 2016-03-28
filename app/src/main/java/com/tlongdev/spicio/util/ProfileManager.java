package com.tlongdev.spicio.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tlongdev.spicio.SpicioApplication;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
public class ProfileManager {

    private static String PREF_KEY_FACEBOOK_ID = "pref_facebook_id";
    private static String PREF_KEY_GOOGLE_ID = "pref_google_id";

    @Inject SharedPreferences mPrefs;
    @Inject SharedPreferences.Editor mEditor;
    @Inject Gson mGson;

    public ProfileManager(SpicioApplication application) {
        application.getProfileManagerComponent().inject(this);
    }

    public boolean isLoggedIn() {
        return mPrefs.contains(PREF_KEY_FACEBOOK_ID) || mPrefs.contains(PREF_KEY_GOOGLE_ID);
    }

    public void logout() {
        mEditor.remove(PREF_KEY_FACEBOOK_ID);
        mEditor.remove(PREF_KEY_GOOGLE_ID);
        mEditor.apply();
    }

    public void loginWithFacebook(String id) {
        mEditor.putString(PREF_KEY_FACEBOOK_ID, id);
        mEditor.apply();
    }

    public void loginWithGoogle(String id) {
        mEditor.putString(PREF_KEY_GOOGLE_ID, id);
        mEditor.apply();
    }

    public String getFacebookId(){
        return mPrefs.getString(PREF_KEY_FACEBOOK_ID, null);
    }

    public String getGoogleId() {
        return mPrefs.getString(PREF_KEY_GOOGLE_ID, null);
    }
}
