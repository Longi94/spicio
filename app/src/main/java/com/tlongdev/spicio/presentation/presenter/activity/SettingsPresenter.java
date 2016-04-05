package com.tlongdev.spicio.presentation.presenter.activity;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.storage.DeleteAllDataInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.DeleteAllDataInteractorImpl;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SettingsView;
import com.tlongdev.spicio.util.Logger;
import com.tlongdev.spicio.util.ProfileManager;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
public class SettingsPresenter implements Presenter<SettingsView>,DeleteAllDataInteractor.Callback {

    private static final String LOG_TAG = SettingsPresenter.class.getSimpleName();

    @Inject ProfileManager mProfileManager;
    @Inject GoogleApiClient mGoogleApiClient;
    @Inject Logger mLogger;

    private SettingsView mView;
    private SpicioApplication mApplication;

    public SettingsPresenter(SpicioApplication application) {
        mApplication = application;
        application.getPresenterComponent().inject(this);
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public void attachView(SettingsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void logout() {
        mProfileManager.logout();

        LoginManager.getInstance().logOut();

        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallbacks<Status>() {
                        @Override
                        public void onSuccess(@NonNull Status status) {
                            mLogger.debug(LOG_TAG, "onSuccess: ");
                        }

                        @Override
                        public void onFailure(@NonNull Status status) {
                            mLogger.debug(LOG_TAG, "onFailure: ");
                        }
                    }
            );
        }

        DeleteAllDataInteractor interactor = new DeleteAllDataInteractorImpl(
                mApplication, this
        );
        interactor.execute();
    }

    public void connectGoogleApiClient() {
        mGoogleApiClient.connect();
    }

    public void disconnectGoogleApiClient() {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onFinish() {
        if (mView != null) {
            mView.startLoginActivity();
        }
    }
}