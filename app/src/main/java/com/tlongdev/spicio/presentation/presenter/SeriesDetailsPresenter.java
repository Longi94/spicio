package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.presentation.ui.activity.SeriesDetailsView;
import com.tlongdev.spicio.threading.MainThread;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public class SeriesDetailsPresenter extends AbstractPresenter implements Presenter<SeriesDetailsView> {

    private SeriesDetailsView mView;

    public SeriesDetailsPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }

    @Override
    public void attachView(SeriesDetailsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
