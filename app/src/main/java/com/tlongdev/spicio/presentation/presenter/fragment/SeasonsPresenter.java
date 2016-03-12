package com.tlongdev.spicio.presentation.presenter.fragment;

import com.tlongdev.spicio.domain.interactor.LoadSeasonsInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonsInteractorImpl;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.SeasonsView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class SeasonsPresenter implements Presenter<SeasonsView>,LoadSeasonsInteractor.Callback {

    private SeasonsView mView;

    @Override
    public void attachView(SeasonsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadSeasons(int seriesId) {
        LoadSeasonsInteractor interactor = new LoadSeasonsInteractorImpl(
                mView.getSpicioApplication(), seriesId, this
        );
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
