package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.interactor.spicio.AddFriendInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetHistoryInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetUserDataInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.RemoveFriendInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.AddFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetHistoryInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetUserDataInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.RemoveFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.DeleteFriendInteractor;
import com.tlongdev.spicio.domain.interactor.storage.IsFriendInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SaveFriendInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.DeleteFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.IsFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveFriendInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.UserView;
import com.tlongdev.spicio.util.ProfileManager;

import java.util.List;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.17.
 */
public class UserPresenter implements Presenter<UserView>,GetUserDataInteractor.Callback, GetHistoryInteractor.Callback, IsFriendInteractor.Callback, RemoveFriendInteractor.Callback, AddFriendInteractor.Callback, DeleteFriendInteractor.Callback, SaveFriendInteractor.Callback {

    @Inject ProfileManager mProfileManager;

    private UserView mView;

    private SpicioApplication mApplication;
    private long mFriendId;

    private User mUser;

    public UserPresenter(SpicioApplication application, long friendId) {
        application.getPresenterComponent().inject(this);
        mApplication = application;
        mFriendId = friendId;
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
        GetUserDataInteractor interactor = new GetUserDataInteractorImpl(mApplication, mFriendId, this);
        interactor.execute();
    }

    @Override
    public void onGetUserDataFinish(User user) {
        mUser = user;
        GetHistoryInteractor interactor = new GetHistoryInteractorImpl(mApplication, mFriendId, this);
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

    public void addOrRemoveFriend() {
        IsFriendInteractor interactor = new IsFriendInteractorImpl(mApplication, mFriendId, this);
        interactor.execute();
    }

    @Override
    public void onIsFriendFinish(boolean friend) {
        Interactor interactor;
        if (friend) {
            interactor = new RemoveFriendInteractorImpl(
                    mApplication, mProfileManager.getId(), mFriendId, this
            );
        } else {
            interactor = new AddFriendInteractorImpl(
                    mApplication, mProfileManager.getId(), mFriendId, this
            );
        }
        interactor.execute();
    }

    @Override
    public void onRemoveFriendFinish() {
        DeleteFriendInteractor interactor = new DeleteFriendInteractorImpl(
                mApplication, mFriendId, this
        );
        interactor.execute();
    }

    @Override
    public void onRemoveFriendFail() {
        mView.showErrorToast();
    }

    @Override
    public void onAddFriendFinish() {
        SaveFriendInteractor interactor = new SaveFriendInteractorImpl(
                mApplication, mUser, this
        );
        interactor.execute();
    }

    @Override
    public void onAddFriendFail() {
        mView.showErrorToast();
    }

    @Override
    public void onDeleteFriendFinish() {
        mView.friendDeleted();
    }

    @Override
    public void onSaveFriendFinish() {
        mView.friendAdded();
    }
}
