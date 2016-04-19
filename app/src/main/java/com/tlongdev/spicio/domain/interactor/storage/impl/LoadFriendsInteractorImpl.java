package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LoadFriendsInteractor;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.19.
 */
public class LoadFriendsInteractorImpl extends AbstractInteractor implements LoadFriendsInteractor {

    private static final String LOG_TAG = LoadFriendsInteractorImpl.class.getSimpleName();

    @Inject UserDao mUserDao;
    @Inject Logger mLogger;

    private Callback mCallback;

    public LoadFriendsInteractorImpl(SpicioApplication application, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        postFinish(mUserDao.getFriends());
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish(final List<User> friends) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLoadFriendsFinish(friends);
                }
            });
        }
    }
}