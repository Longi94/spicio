package com.tlongdev.spicio.presentation.presenter;

import com.tlongdev.spicio.presentation.ui.view.BaseView;

/**
 * Middle layer, Presenter.
 * The base presenter interface for the MVP architecture.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public interface Presenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
}
