package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.AddFriendInteractor;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class AddFriendInteractorImpl extends AbstractInteractor implements AddFriendInteractor {

    private static final String LOG_TAG = AddFriendInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject SpicioRepository mRepository;

    private long mUserId;
    private long mFriendId;
    private Callback mCallback;

    public AddFriendInteractorImpl(SpicioApplication application, long userId, long friendId, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUserId = userId;
        mFriendId = friendId;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");

        if (mRepository.addFriend(mUserId, mFriendId)) {
            postFinish();
        } else {
            postFail();
        }

        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAddFriendFinish();
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onAddFriendFail();
                }
            });
        }
    }
}