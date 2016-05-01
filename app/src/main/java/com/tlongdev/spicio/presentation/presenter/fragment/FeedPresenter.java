package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.spicio.GetFeedInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetFeedInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.LoadFeedInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SaveFeedInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadFeedInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveFeedInteractorImpl;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.FeedView;
import com.tlongdev.spicio.util.ProfileManager;

import java.util.List;

import javax.inject.Inject;

/**
 * @author longi
 * @since 2016.04.22.
 */
public class FeedPresenter implements Presenter<FeedView>,GetFeedInteractor.Callback, LoadFeedInteractor.Callback, SaveFeedInteractor.Callback {

    @Inject ProfileManager mProfileManager;

    private SpicioApplication mApplication;

    private FeedView mView;

    public FeedPresenter(SpicioApplication application) {
        mApplication = application;
        application.getPresenterComponent().inject(this);
    }

    @Override
    public void attachView(FeedView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void getFeed() {
        mView.showLoadingAnimation();
        GetFeedInteractor interactor = new GetFeedInteractorImpl(
                mApplication, mProfileManager.getId(), this
        );
        interactor.execute();
    }

    @Override
    public void onGetFeedFinish(List<UserActivity> activities) {
        if (mView != null) {
            mView.hideLoadingAnimation();
            mView.showFeed(activities);
        }
        SaveFeedInteractor interactor = new SaveFeedInteractorImpl(mApplication, activities, this);
        interactor.execute();
    }

    @Override
    public void onGetFeedFail() {
        if (mView != null) {
            mView.hideLoadingAnimation();
        }
    }

    public void onRefresh() {
        GetFeedInteractor interactor = new GetFeedInteractorImpl(
                mApplication, mProfileManager.getId(), this
        );
        interactor.execute();
    }

    public void loadFeed() {
        LoadFeedInteractor interactor = new LoadFeedInteractorImpl(mApplication, this);
        interactor.execute();
    }

    @Override
    public void onLoadFeedFinish(List<UserActivity> activities) {
        if (mView != null) {
            mView.showFeed(activities);
        }
    }

    @Override
    public void onSaveFeedFinish() {

    }
}