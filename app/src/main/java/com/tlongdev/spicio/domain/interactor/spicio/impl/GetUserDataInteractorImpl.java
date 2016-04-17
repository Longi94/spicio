package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetUserDataInteractor;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserFull;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.17.
 */
public class GetUserDataInteractorImpl extends AbstractInteractor implements GetUserDataInteractor {

    private static final String LOG_TAG = GetUserDataInteractorImpl.class.getSimpleName();

    @Inject SpicioRepository mRepository;
    @Inject Logger mLogger;

    private long mId;
    private Callback mCallback;

    public GetUserDataInteractorImpl(SpicioApplication application, long id, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mId = id;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        UserFull user = mRepository.getUser(mId, false);

        if (user != null) {
            postFinish(user.getUser());
        } else {
            postFail();
        }
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish(final User user) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetUserDataFinish(user);
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetUserDataFail();
                }
            });
        }
    }
}