package com.tlongdev.spicio.presentation.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.presentation.presenter.activity.LoginPresenter;
import com.tlongdev.spicio.presentation.ui.view.activity.LoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends SpicioActivity implements LoginView {

    @Bind(R.id.facebook_login) LoginButton mFacebookLoginButton;
    @Bind(R.id.google_login) SignInButton mGoogleLoginButton;

    private LoginPresenter mPresenter;

    private ProgressDialog mProgressDialog;

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
    public void showLoadingAnim() {
        mProgressDialog = ProgressDialog.show(this, null, null, true, false);
    }

    @Override
    public void hideLoadingAnim() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onLogin() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.google_login)
    public void googleLogin() {
        mPresenter.googleLogin();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}
