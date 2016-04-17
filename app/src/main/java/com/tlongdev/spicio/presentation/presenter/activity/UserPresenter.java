package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.spicio.GetHistoryInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetUserDataInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetHistoryInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetUserDataInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserView;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.17.
 */
public class UserPresenter implements Presenter<UserView>,GetUserDataInteractor.Callback, GetHistoryInteractor.Callback {

    private UserView mView;

    private SpicioApplication mApplication;
    private long mUserId;

    private User mUser;

    public UserPresenter(SpicioApplication application, long userId) {
        mApplication = application;
        mUserId = userId;
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
        GetUserDataInteractor interactor = new GetUserDataInteractorImpl(mApplication, mUserId, this);
        interactor.execute();
    }

    @Override
    public void onGetUserDataFinish(User user) {
        mUser = user;
        GetHistoryInteractor interactor = new GetHistoryInteractorImpl(mApplication, mUserId, this);
        interactor.execute();
    }

    @Override
    public void onGetUserDataFail() {
        if (mView != null) {
            mView.showError();
        }
    }

    @Override
    public void onGetHistoryFinish(List<UserActivity> history) {
        if (mView != null) {
            mView.showUserData(mUser, history);
        }
    }

    @Override
    public void onGetHistoryFail() {
        if (mView != null) {
            mView.showError();
        }
    }
}
