package com.tlongdev.spicio.presentation.ui.view.activity;

import com.tlongdev.spicio.presentation.ui.view.BaseView;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
public interface LoginView extends BaseView {

    void onFaceBookLoginSuccess();

    void onFaceBookLoginCancel();

    void onFaceBookLoginFail();
}