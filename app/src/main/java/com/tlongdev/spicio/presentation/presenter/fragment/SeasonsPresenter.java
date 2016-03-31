package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.interactor.storage.LoadSeasonsInteractor;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadSeasonsInteractorImpl;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeasonsView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class SeasonsPresenter implements Presenter<SeasonsView>,LoadSeasonsInteractor.Callback {

    private SeasonsView mView;

    private SpicioApplication mApplication;

    public SeasonsPresenter(SpicioApplication application) {
        mApplication = application;
    }

    @Override
    public void attachView(SeasonsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadSeasons(int seriesId) {
        LoadSeasonsInteractor interactor = new LoadSeasonsInteractorImpl(mApplication, seriesId, this);
        interactor.execute();
    }

    @Override
    public void onLoadSeasonsFinish(List<Season> seasons) {
        if (mView != null) {
            mView.showSeasons(seasons);
        }
    }

    @Override
    public void onLoadSeasonsFail() {

    }
}
