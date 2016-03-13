package com.tlongdev.spicio.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.presentation.presenter.activity.LoginPresenter;
import com.tlongdev.spicio.presentation.ui.view.activity.LoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.facebook_login) LoginButton mFacebookLoginButton;
    @Bind(R.id.google_login) SignInButton mGoogleLoginButton;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SpicioApplication application = (SpicioApplication) getApplication();
        application.getActivityComponent().inject(this);

        mPresenter = new LoginPresenter(application);
        mPresenter.attachView(this);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //Set the color of the status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        mFacebookLoginButton.setReadPermissions("email");

        mPresenter.initGoogleSignIn(mGoogleLoginButton);
        mPresenter.registerFacebookCallback(mFacebookLoginButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.connectGoogleApiClient();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.disconnectGoogleApiClient();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        //no-op
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public SpicioApplication getSpicioApplication() {
        return (SpicioApplication) getApplication();
    }

    @Override
    public void onFaceBookLoginSuccess() {
        Toast.makeText(this, "facebook success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFaceBookLoginCancel() {
        Toast.makeText(this, "facebook cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFaceBookLoginFail() {
        Toast.makeText(this, "facebook error", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.logout)
    public void logOut() {
        mPresenter.logOut();
    }

    @OnClick(R.id.google_login)
    public void googleLogin() {
        mPresenter.googleLogin();
    }
}
