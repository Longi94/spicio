package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetFriendsInteractor;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.15.
 */
public class GetFriendsInteractorImpl extends AbstractInteractor implements GetFriendsInteractor {

    private static final String LOG_TAG = GetFriendsInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject SpicioRepository mRepository;

    private long mUserId;
    private Callback mCallback;

    public GetFriendsInteractorImpl(SpicioApplication application, long userId, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUserId = userId;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        List<User> friends = mRepository.getFriends(mUserId);

        if (friends != null) {
            postFinish(friends);
        } else {
            postFail();
        }
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish(final List<User> friends) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetFriendsFinish(friends);
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetFriendsFail();
                }
            });
        }
    }
}