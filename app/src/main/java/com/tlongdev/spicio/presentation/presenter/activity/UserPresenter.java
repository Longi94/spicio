package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserView;

/**
 * @author longi
 * @since 2016.04.17.
 */
public class UserPresenter implements Presenter<UserView> {

    private UserView mView;

    private SpicioApplication mApplication;

    public UserPresenter(SpicioApplication application) {
        mApplication = application;
    }

    @Override
    public void attachView(UserView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void getUserData() {

    }
}
