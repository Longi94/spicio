package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.presentation.ui.activity.MainView;
import com.tlongdev.spicio.threading.MainThread;

/**
 * Middle layer, Presenter.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public class MainPresenter extends AbstractPresenter implements Presenter<MainView> {

    private MainView mView;

    public MainPresenter(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
    }

    @Override
    public void attachView(MainView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
