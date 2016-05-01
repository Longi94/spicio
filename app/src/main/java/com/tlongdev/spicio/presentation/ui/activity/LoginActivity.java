package com.tlongdev.spicio.presentation.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.presentation.presenter.activity.LoginPresenter;
import com.tlongdev.spicio.presentation.ui.view.activity.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends SpicioActivity implements LoginView {

    @Inject LoginPresenter mPresenter;

    @BindView(R.id.facebook_login) LoginButton mFacebookLoginButton;
    @BindView(R.id.google_login) SignInButton mGoogleLoginButton;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mApplication.getActivityComponent().inject(this);

        mPresenter.attachView(this);

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

    @Override
    public void showProgressDialog(int max) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setTitle("Getting your series");
        mProgressDialog.setMax(max);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void updateProgress(String title) {
        mProgressDialog.setMessage(title);
        mProgressDialog.incrementProgressBy(1);
    }

    @Override
    public void showError() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
    }
}
