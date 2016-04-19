package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.DeleteFriendInteractor;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.19.
 */
public class DeleteFriendInteractorImpl extends AbstractInteractor implements DeleteFriendInteractor {

    private static final String LOG_TAG = DeleteFriendInteractorImpl.class.getSimpleName();

    @Inject UserDao mUserDao;
    @Inject Logger mLogger;

    private long mFriendId;
    private Callback mCallback;

    public DeleteFriendInteractorImpl(SpicioApplication application, long friendId, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mFriendId = friendId;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        mUserDao.removeFriend(mFriendId);
        postFinish();
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onDeleteFriendFinish();
                }
            });
        }
    }
}