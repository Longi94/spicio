package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.spicio.SearchUsersInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SearchUsersInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SearchFriendsView;
import com.tlongdev.spicio.util.ProfileManager;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public class SearchFriendsPresenter implements Presenter<SearchFriendsView>, SearchUsersInteractor.Callback {

    @Inject ProfileManager mProfileManager;

    private SearchFriendsView mView;

    private SpicioApplication mApplication;

    public SearchFriendsPresenter(SpicioApplication application) {
        mApplication = application;
        application.getPresenterComponent().inject(this);
    }

    @Override
    public void attachView(SearchFriendsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void search(String query) {
        SearchUsersInteractor interactor = new SearchUsersInteractorImpl(
                mApplication, query, mProfileManager.getId(), this
        );
        interactor.execute();
    }

    @Override
    public void onSearchFinished(List<User> users) {
        if (mView != null) {
            mView.showResult(users);
        }
    }
}
