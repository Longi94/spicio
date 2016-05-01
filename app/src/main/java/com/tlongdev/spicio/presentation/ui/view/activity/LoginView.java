package com.tlongdev.spicio.presentation.ui.view.activity;

import android.content.Intent;

import com.tlongdev.spicio.presentation.ui.view.BaseView;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
public interface LoginView extends BaseView {
    void showLoadingAnim();

    void hideLoadingAnim();

    void onLogin();

    void startActivityForResult(Intent intent, int requestCode);

    void showProgressDialog(int max);

    void updateProgress(String title);

    void showError();
}