package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.SearchUsersInteractor;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public class SearchUsersInteractorImpl extends AbstractInteractor implements SearchUsersInteractor {

    private static final String LOG_TAG = SearchUsersInteractorImpl.class.getSimpleName();

    @Inject SpicioRepository mRepository;
    @Inject Logger mLogger;

    private String mQuery;
    private long mIgnore;
    private Callback mCallback;

    public SearchUsersInteractorImpl(SpicioApplication application, String query, long ignore, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mQuery = query;
        mIgnore = ignore;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.verbose(LOG_TAG, "started");

        List<User> users = mRepository.searchUser(mQuery, mIgnore);
        postResult(users);

        mLogger.verbose(LOG_TAG, "ended");
    }

    public void postResult(final List<User> users) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSearchFinished(users);
                }
            });
        }
    }
}
