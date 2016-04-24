package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SaveFeedInteractor;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 04. 24.
 */
public class SaveFeedInteractorImpl extends AbstractInteractor implements SaveFeedInteractor {

    private static final String LOG_TAG = SaveFeedInteractorImpl.class.getSimpleName();

    @Inject Logger mLogger;
    @Inject UserDao mUserDao;

    private List<UserActivity> mActivities;
    private Callback mCallback;

    public SaveFeedInteractorImpl(SpicioApplication application, List<UserActivity> activities, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mActivities = activities;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.debug(LOG_TAG, "started");
        mUserDao.insertFeed(mActivities);
        postFinish();
        mLogger.debug(LOG_TAG, "stopped");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSaveFeedFinish();
                }
            });
        }
    }
}