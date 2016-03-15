package com.tlongdev.spicio.presentation.presenter.activity;

import com.tlongdev.spicio.presentation.presenter.Presenter;
import com.tlongdev.spicio.presentation.ui.view.activity.MainView;

/**
 * Middle layer, Presenter.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public class MainPresenter implements Presenter<MainView> {

    private MainView mView;

    @Override
    public void attachView(MainView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
