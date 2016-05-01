package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.spicio.GetFriendsInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetFriendsInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.ui.view.activity.UserFriendsView;
import com.tlongdev.spicio.util.ProfileManager;

import java.util.List;

import javax.inject.Inject;

/**
 * @author lngtr
 * @since 2016. 05. 01.
 */
public class UserFriendsPresenter implements Presenter<UserFriendsView>,GetFriendsInteractor.Callback {

    @Inject ProfileManager mProfileManager;

    private SpicioApplication mApplication;

    private UserFriendsView mView;

    public UserFriendsPresenter(SpicioApplication application) {
        mApplication = application;
        application.getPresenterComponent().inject(this);
    }

    @Override
    public void attachView(UserFriendsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void getFriends(long userId) {
        GetFriendsInteractor interactor = new GetFriendsInteractorImpl(
                mApplication, userId, mProfileManager.getId(), this
        );
        interactor.execute();
    }

    @Override
    public void onGetFriendsFinish(List<User> friends) {
        if (mView != null) {
            mView.showFriends(friends);
        }
    }

    @Override
    public void onGetFriendsFail() {
        if (mView != null) {
            mView.showError();
        }
    }
}