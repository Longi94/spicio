package com.tlongdev.spicio.presentation.presenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.LoginView;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
public class LoginPresenter implements Presenter<LoginView>, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = LoginPresenter.class.getSimpleName();

    public static final int RC_SIGN_IN = 100;

    @Inject CallbackManager mCallbackManager;
    @Inject GoogleApiClient mGoogleApiClient;
    @Inject GoogleSignInOptions mGoogleSignInOptions;

    private LoginView mView;

    public LoginPresenter(SpicioApplication application) {
        application.getPresenterComponent().inject(this);
    }

    @Override
    public void attachView(LoginView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void initGoogleSignIn(SignInButton googleLoginButton) {
        mGoogleApiClient.registerConnectionCallbacks(this);
        mGoogleApiClient.registerConnectionFailedListener(this);

        googleLoginButton.setScopes(mGoogleSignInOptions.getScopeArray());
        googleLoginButton.setSize(SignInButton.SIZE_WIDE);
    }

    public void registerFacebookCallback(LoginButton loginButton) {
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mView.onFaceBookLoginSuccess();
            }

            @Override
            public void onCancel() {
                mView.onFaceBookLoginCancel();
            }

            @Override
            public void onError(FacebookException error) {
                mView.onFaceBookLoginFail();
            }
        });
    }

    public void logOut() {
        LoginManager.getInstance().logOut();

        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallbacks<Status>() {
                        @Override
                        public void onSuccess(@NonNull Status status) {
                            Log.d(LOG_TAG, "onSuccess: ");
                        }

                        @Override
                        public void onFailure(@NonNull Status status) {
                            Log.d(LOG_TAG, "onFailure: ");
                        }
                    }
            );
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(LOG_TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                Log.d(LOG_TAG, "onActivityResult: " + acct.getDisplayName() + ", " + acct.getId());
            } else {
                // Signed out, show unauthenticated UI.
                Log.d(LOG_TAG, "onActivityResult: signed out");
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(LOG_TAG, "onConnected: google api client");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "onConnectionSuspended: google api client");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "onConnectionFailed: google api client");
    }

    public void googleLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity)mView.getContext()).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void connectGoogleApiClient() {
        mGoogleApiClient.connect();
    }

    public void disconnectGoogleApiClient() {
        mGoogleApiClient.disconnect();
    }
}