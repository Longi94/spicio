package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.CheckEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.LikeEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.LoadEpisodeDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.impl.CheckEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LikeEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LoadEpisodeDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Watched;
import com.tlongdev.spicio.presentation.presenter.AbstractPresenter;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.EpisodeView;
import com.tlongdev.spicio.threading.MainThread;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public class EpisodePresenter extends AbstractPresenter implements Presenter<EpisodeView>,LoadEpisodeDetailsInteractor.Callback, CheckEpisodeInteractor.Callback, LikeEpisodeInteractor.Callback {

    private EpisodeView mView;

    private Episode mEpisode;

    public EpisodePresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
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
                mExecutor, mMainThread, mView.getSpicioApplication(), episodeId, this
        );
        interactor.execute();
    }

    public void checkEpisode() {
        int watched = mEpisode.isWatched() == Watched.WATCHED ? Watched.NONE : Watched.WATCHED;

        mEpisode.setWatched(watched);
        CheckEpisodeInteractor interactor = new CheckEpisodeInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), mEpisode.getTraktId(),
                watched, this
        );
        interactor.execute();
    }

    public void likeEpisode() {
        LikeEpisodeInteractor interactor = new LikeEpisodeInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), mEpisode.getTraktId(),
                !mEpisode.isLiked(), this
        );
        interactor.execute();
    }

    public void skipEpisode() {
        int watched = mEpisode.isWatched() == Watched.SKIPPED ? Watched.NONE : Watched.SKIPPED;

        mEpisode.setWatched(watched);
        CheckEpisodeInteractor interactor = new CheckEpisodeInteractorImpl(
                mExecutor, mMainThread, mView.getSpicioApplication(), mEpisode.getTraktId(),
                watched, this
        );
        interactor.execute();
    }

    @Override
    public void onEpisodeCheckFinish() {
        if (mView != null) {
            mView.updateCheckButton(mEpisode.isWatched());
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
        }
    }

    @Override
    public void onEpisodeLikeFail() {

    }
}
