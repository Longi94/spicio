package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.spicio.SpicioCheckEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.SpicioLikeEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.SpicioSkipEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SpicioCheckEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SpicioLikeEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SpicioSkipEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.CheckEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LikeEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.storage.LoadEpisodeDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.storage.SkipEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.CheckEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LikeEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadEpisodeDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SkipEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.EpisodeView;
import com.tlongdev.spicio.util.Logger;
import com.tlongdev.spicio.util.ProfileManager;

import javax.inject.Inject;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public class EpisodePresenter implements Presenter<EpisodeView>,
        LoadEpisodeDetailsInteractor.Callback, CheckEpisodeInteractor.Callback,
        LikeEpisodeInteractor.Callback, SkipEpisodeInteractor.Callback, SpicioCheckEpisodeInteractor.Callback, SpicioLikeEpisodeInteractor.Callback, SpicioSkipEpisodeInteractor.Callback {

    private static final String LOG_TAG = EpisodePresenter.class.getSimpleName();

    @Inject ProfileManager mProfileManager;
    @Inject Logger mLogger;

    private EpisodeView mView;

    private Episode mEpisode;

    private SpicioApplication mApplication;

    public EpisodePresenter(SpicioApplication application) {
        mApplication = application;
        application.getPresenterComponent().inject(this);
    }

    @Override
    public void attachView(EpisodeView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onLoadEpisodeDetailsFinish(Episode episode) {
        mEpisode = episode;
        if (mView != null) {
            mView.showEpisodeDetails(episode);
        }
    }

    @Override
    public void onLoadEpisodeDetailsFail() {
        mEpisode = null;
        if (mView != null) {
            mView.showError();
        }
    }

    public void loadEpisode(int episodeId) {
        LoadEpisodeDetailsInteractor interactor = new LoadEpisodeDetailsInteractorImpl(
                mApplication, episodeId, this
        );
        interactor.execute();
    }

    public void checkEpisode() {
        mLogger.verbose(LOG_TAG, "sending check to server...");
        mView.showLoadingDialog();
        SpicioCheckEpisodeInteractor interactor = new SpicioCheckEpisodeInteractorImpl(
                mApplication, mProfileManager.getId(), mEpisode, this
        );
        interactor.execute();
    }

    public void likeEpisode() {
        mLogger.verbose(LOG_TAG, "sending like to server...");
        mView.showLoadingDialog();
        SpicioLikeEpisodeInteractor interactor = new SpicioLikeEpisodeInteractorImpl(
                mApplication, mProfileManager.getId(), mEpisode, this
        );
        interactor.execute();
    }

    public void skipEpisode() {
        mLogger.verbose(LOG_TAG, "sending skip to server...");
        mView.showLoadingDialog();
        SpicioSkipEpisodeInteractor interactor = new SpicioSkipEpisodeInteractorImpl(
                mApplication, mProfileManager.getId(), mEpisode, this
        );
        interactor.execute();
    }

    @Override
    public void onEpisodeCheckFinish() {
        mEpisode.setWatched(!mEpisode.isWatched());
        mEpisode.setSkipped(false);
        if (mView != null) {
            mView.updateCheckButton(mEpisode.isWatched());
            mView.updateSkipButton(false);
            mView.dismissLoadingDialog();
        }
    }

    @Override
    public void onEpisodeCheckFail() {

    }

    @Override
    public void onEpisodeLikeFinish() {
        mEpisode.setLiked(!mEpisode.isLiked());
        if (mView != null) {
            mView.updateLikeButton(mEpisode.isLiked());
            mView.dismissLoadingDialog();
        }
    }

    @Override
    public void onEpisodeLikeFail() {

    }

    @Override
    public void onEpisodeSkipFinish() {
        mEpisode.setSkipped(!mEpisode.isSkipped());
        mEpisode.setWatched(false);
        if (mView != null) {
            mView.updateSkipButton(mEpisode.isSkipped());
            mView.updateCheckButton(false);
            mView.dismissLoadingDialog();
        }
    }

    @Override
    public void onEpisodeSkipFail() {

    }

    @Override
    public void onSpicioCheckFinish() {
        mLogger.verbose(LOG_TAG, "saving check to db...");
        CheckEpisodeInteractor interactor = new CheckEpisodeInteractorImpl(
                mApplication, mEpisode.getSeriesId(), mEpisode.getTraktId(), !mEpisode.isWatched(), this
        );
        interactor.execute();
    }

    @Override
    public void onSpicioCheckFail() {
        mLogger.verbose(LOG_TAG, "failed to sending check to server");
        if (mView != null) {
            mView.dismissLoadingDialog();
        }
    }

    @Override
    public void onSpicioLikeFinish() {
        mLogger.verbose(LOG_TAG, "saving like to db...");
        LikeEpisodeInteractor interactor = new LikeEpisodeInteractorImpl(
                mApplication, mEpisode.getSeriesId(), mEpisode.getTraktId(), !mEpisode.isLiked(), this
        );
        interactor.execute();
    }

    @Override
    public void onSpicioLikeFail() {
        mLogger.verbose(LOG_TAG, "failed to sending like to server");
        if (mView != null) {
            mView.dismissLoadingDialog();
        }
    }

    @Override
    public void onSpicioSkipFinish() {
        mLogger.verbose(LOG_TAG, "saving skip to db...");
        SkipEpisodeInteractor interactor = new SkipEpisodeInteractorImpl(
                mApplication, mEpisode.getSeriesId(), mEpisode.getTraktId(), !mEpisode.isSkipped(), this
        );
        interactor.execute();
    }

    @Override
    public void onSpicioSkipFail() {
        mLogger.verbose(LOG_TAG, "failed to sending skip to server");
        if (mView != null) {
            mView.dismissLoadingDialog();
        }
    }
}
