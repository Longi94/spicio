package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.presentation.ui.view.fragment.SeriesView;
import com.tlongdev.spicio.threading.MainThread;

/**
 * @author Long
 * @since 2016. 03. 06.
 */
public class SeriesPresenter extends AbstractPresenter implements Presenter<SeriesView> {

    private SeriesView mView;

    public SeriesPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }

    @Override
    public void attachView(SeriesView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
