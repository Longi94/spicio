package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SaveFriendInteractor;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.19.
 */
public class SaveFriendInteractorImpl extends AbstractInteractor implements SaveFriendInteractor {

    private static final String LOG_TAG = SaveFriendInteractorImpl.class.getSimpleName();

    @Inject UserDao mUserDao;
    @Inject Logger mLogger;

    private User mFriend;
    private Callback mCallback;

    public SaveFriendInteractorImpl(SpicioApplication application, User friend, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mFriend = friend;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        mUserDao.addFriend(mFriend);
        postFinish();
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSaveFriendFinish();
                }
            });
        }
    }
}