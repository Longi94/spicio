package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.LoadEpisodeDetailsInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadEpisodeDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.presenter.AbstractPresenter;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.EpisodeView;
import com.tlongdev.spicio.threading.MainThread;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public class EpisodePresenter extends AbstractPresenter implements Presenter<EpisodeView>,LoadEpisodeDetailsInteractor.Callback {

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
}
