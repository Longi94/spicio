package com.tlongdev.spicio.domain.interactor.storage.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SaveActivitiesInteractor;
import com.tlongdev.spicio.domain.model.SeriesActivities;
import com.tlongdev.spicio.storage.dao.UserDao;
import com.tlongdev.spicio.util.Logger;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public class SaveActivitiesInteractorImpl extends AbstractInteractor implements SaveActivitiesInteractor {

    private static final String LOG_TAG = SaveActivitiesInteractorImpl.class.getSimpleName();

    @Inject UserDao mUserDao;
    @Inject Logger mLogger;

    private Map<Integer, SeriesActivities> mSeriesActivities;
    private Callback mCallback;

    public SaveActivitiesInteractorImpl(SpicioApplication application, Map<Integer, SeriesActivities> seriesActivities, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mSeriesActivities = seriesActivities;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.verbose(LOG_TAG, "started");

        mUserDao.insertUserActivities(mSeriesActivities);
        postFinish();

        mLogger.verbose(LOG_TAG, "ended");
    }

    private void postFinish() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSaveActivitiesFinish();
                }
            });
        }
    }
}