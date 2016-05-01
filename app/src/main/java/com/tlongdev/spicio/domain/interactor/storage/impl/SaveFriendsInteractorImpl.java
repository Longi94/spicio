package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SaveFriendsInteractor;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author lngtr
 * @since 2016. 05. 01.
 */
public class SaveFriendsInteractorImpl extends AbstractInteractor implements SaveFriendsInteractor {

    private static final String LOG_TAG = SaveFriendsInteractorImpl.class.getSimpleName();

    @Inject UserDao mUserDao;
    @Inject Logger mLogger;

    private List<User> mFriends;
    private Callback mCallback;

    public SaveFriendsInteractorImpl(SpicioApplication application, List<User> friends, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mFriends = friends;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        mUserDao.addFriends(mFriends);
        postFinish();
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSaveFriendsFinish();
                }
            });
        }
    }
}