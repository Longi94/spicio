package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.IsFriendInteractor;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.19.
 */
public class IsFriendInteractorImpl extends AbstractInteractor implements IsFriendInteractor {

    private static final String LOG_TAG = IsFriendInteractorImpl.class.getSimpleName();

    @Inject UserDao mUserDao;
    @Inject Logger mLogger;

    private long mFriendId;
    private Callback mCallback;

    public IsFriendInteractorImpl(SpicioApplication application, long friendId, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mFriendId = friendId;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        postFinish(mUserDao.isFriend(mFriendId));
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish(final boolean isFriend) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onIsFriendFinish(isFriend);
                }
            });
        }
    }
}