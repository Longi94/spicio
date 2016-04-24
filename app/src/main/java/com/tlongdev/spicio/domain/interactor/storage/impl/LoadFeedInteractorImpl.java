package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LoadFeedInteractor;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 04. 24.
 */
public class LoadFeedInteractorImpl extends AbstractInteractor implements LoadFeedInteractor {

    private static final String LOG_TAG = LoadFeedInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject UserDao mUserDao;

    private Callback mCallback;

    public LoadFeedInteractorImpl(SpicioApplication application, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        List<UserActivity> activities = mUserDao.getFeed();
        postFinish(activities);
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish(final List<UserActivity> activities) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLoadFeedFinish(activities);
                }
            });
        }
    }
}