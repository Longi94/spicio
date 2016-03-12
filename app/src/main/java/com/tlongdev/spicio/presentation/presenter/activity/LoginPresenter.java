package com.tlongdev.spicio.presentation.presenter.activity;

import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.LoginView;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
public class LoginPresenter implements Presenter<LoginView> {

    private LoginView mView;

    private CallbackManager mCallbackManager;

    @Override
    public void attachView(LoginView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void registerFacebookCallback(LoginButton loginButton) {
        mCallbackManager = CallbackManager.Factory.create();
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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}