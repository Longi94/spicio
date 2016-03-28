package com.tlongdev.spicio.presentation.presenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.LoginView;
import com.tlongdev.spicio.util.Logger;
import com.tlongdev.spicio.util.ProfileManager;

import org.json.JSONObject;

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
    @Inject ProfileManager mProfileManager;
    @Inject Logger mLogger;

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
                
                if (mView != null) {
                    mView.showLoadingAnim();
                }

                final AccessToken accessToken = loginResult.getAccessToken();

                GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        mLogger.debug(LOG_TAG, "email: " + user.optString("email"));
                        mLogger.debug(LOG_TAG, "name: " + user.optString("name"));
                        mLogger.debug(LOG_TAG, "id: " + user.optString("id"));
                        
                        String facebookId = user.optString("id");
                        
                        if (facebookId == null || facebookId.isEmpty()) {
                            throw new IllegalStateException("Couldn't get facebook ID");
                        }
                        //mProfileManager.login(facebookId);

                        if (mView != null) {
                            mView.hideLoadingAnim();
                            mView.onLogin();
                        }
                    }
                }).executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                mLogger.debug(LOG_TAG, "onError: " + error.getMessage());
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mLogger.debug(LOG_TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                mLogger.debug(LOG_TAG, "onActivityResult: " + acct.getDisplayName() + ", " + acct.getId());
                //mProfileManager.loginWithGoogle(acct.getId());
                
                if (mView != null) {
                    mView.hideLoadingAnim();
                    mView.onLogin();
                }
            } else {
                // Signed out, show unauthenticated UI.
                mLogger.debug(LOG_TAG, "onActivityResult: signed out");
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLogger.debug(LOG_TAG, "onConnected: google api client");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mLogger.debug(LOG_TAG, "onConnectionSuspended: google api client");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mLogger.debug(LOG_TAG, "onConnectionFailed: google api client");
    }

    public void googleLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mView.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void connectGoogleApiClient() {
        mGoogleApiClient.connect();
    }

    public void disconnectGoogleApiClient() {
        mGoogleApiClient.disconnect();
    }
}