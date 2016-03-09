package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.LoadSeasonsInteractor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonsInteractorImpl;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.view.activity.SeasonsView;
import com.tlongdev.spicio.threading.MainThread;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class SeasonsPresenter extends AbstractPresenter implements Presenter<SeasonsView>,LoadSeasonsInteractor.Callback {

    private SeasonsView mView;

    public SeasonsPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
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
        LoadSeasonsInteractor interactor = new LoadSeasonsInteractorImpl(
                mExecutor,
                mMainThread,
                mView.getSpicioApplication(),
                seriesId,
                this
        );
        interactor.execute();
    }

    @Override
    public void onFinish(List<Season> seasons) {
        mView.showSeasons(seasons);
    }

    @Override
    public void onFail() {

    }
}
