package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.storage.LoadFriendsInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadFriendsInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.FriendsView;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.19.
 */
public class FriendsPresenter implements Presenter<FriendsView>,LoadFriendsInteractor.Callback {

    private SpicioApplication mApplication;

    private FriendsView mView;

    public FriendsPresenter(SpicioApplication application) {
        mApplication = application;
    }

    @Override
    public void attachView(FriendsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadFriends() {
        LoadFriendsInteractor interactor = new LoadFriendsInteractorImpl(
                mApplication, this
        );
        interactor.execute();
    }

    @Override
    public void onLoadFriendsFinish(List<User> friends) {
        if (mView != null) {
            mView.showFriends(friends);
        }
    }
}