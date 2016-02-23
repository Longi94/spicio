package com.tlongdev.spicio.presenter;

/**
 * Created by Long on 2016. 02. 23..
 */
public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
