package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.SearchUsersInteractor;
import com.tlongdev.spicio.domain.interactor.impl.SearchUsersInteractorImpl;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SearchFriendsView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public class SearchFriendsPresenter implements Presenter<SearchFriendsView>, SearchUsersInteractor.Callback {

    private SearchFriendsView mView;

    private SpicioApplication mApplication;

    public SearchFriendsPresenter(SpicioApplication application) {
        mApplication = application;
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
        SearchUsersInteractor interactor = new SearchUsersInteractorImpl(mApplication, query, this);
        interactor.execute();
    }

    @Override
    public void onSearchFinished(List<User> users) {
        if (mView != null) {
            mView.showResult(users);
        }
    }
}
