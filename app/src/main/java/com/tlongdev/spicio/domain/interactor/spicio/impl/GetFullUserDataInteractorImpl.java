package com.tlongdev.spicio.domain.interactor.spicio.impl;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.GetFullUserDataInteractor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.SeriesActivities;
import com.tlongdev.spicio.domain.model.UserFull;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.util.Logger;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public class GetFullUserDataInteractorImpl extends AbstractInteractor implements GetFullUserDataInteractor {

    private static final String LOG_TAG = GetFullUserDataInteractorImpl.class.getName();

    @Inject SpicioRepository mRepository;
    @Inject Logger mLogger;

    private long mId;
    private Callback mCallback;

    public GetFullUserDataInteractorImpl(SpicioApplication application, long id, Callback callback) {
        super(application.getInteractorComponent());
        application.getInteractorComponent().inject(this);
        mId = id;
        mCallback = callback;
    }

    @Override
    public void run() {
        mLogger.verbose(LOG_TAG, "started");
        UserFull user = mRepository.getUser(mId, true);

        if (user != null) {
            postFinish(user.getUser(), user.getSeries(), user.getActivities(), user.getFriends());
        } else {
            postFail();
        }

        mLogger.verbose(LOG_TAG, "ended");
    }

    void postFinish(final User user, final List<Series> series, final Map<Integer, SeriesActivities> activities, final List<User> friends) {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetFullUserDataFinished(user, series, activities, friends);
                }
            });
        }
    }

    void postFail() {
        if (mCallback != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onGetFullUserDataFail();
                }
            });
        }
    }
}
