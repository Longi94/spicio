package com.tlongdev.spicio.presentation.presenter;

/**
 * Middle layer, Presenter.
 * The base presenter interface for the MVP architecture.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
