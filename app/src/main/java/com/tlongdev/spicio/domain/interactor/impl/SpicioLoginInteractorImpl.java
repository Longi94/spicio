package com.tlongdev.spicio.domain.interactor.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.SpicioLoginInteractor;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.03.28.
 */
public class SpicioLoginInteractorImpl extends AbstractInteractor implements SpicioLoginInteractor {

    private static final String LOG_TAG = SpicioLoginInteractorImpl.class.getSimpleName();

    @Inject SpicioRepository mRepository;
    @Inject Logger mLogger;

    private User mUser;
    private Callback mCallback;

    public SpicioLoginInteractorImpl(SpicioApplication application, User user, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mUser = user;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");

        long id = mRepository.login(mUser);

        if (id >= 0) {
            postSuccess(id);
        } else {
            postFail();
        }

        mLogger.debug(LOG_TAG, "finished");
    }

    private void postSuccess(final long id) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLoginSuccess(id);
                }
            });
        }
    }

    private void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLoginFailed();
                }
            });
        }
    }
}
