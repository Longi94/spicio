package com.tlongdev.spicio.module;

import android.app.Application;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.util.ProfileManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
@Module
public class AuthenticationModule {

    @Provides
    @Singleton
    GoogleSignInOptions provideGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    @Provides
    @Singleton
    GoogleApiClient provideGoogleApiClient(Application application, GoogleSignInOptions googleSignInOptions) {
        return new GoogleApiClient.Builder(application)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    @Provides
    @Singleton
    CallbackManager provideCallbackManager() {
        return CallbackManager.Factory.create();
    }

    @Provides
    @Singleton
    ProfileManager provideProfileManager(Application application) {
        return ProfileManager.getInstance((SpicioApplication) application);
    }
}
