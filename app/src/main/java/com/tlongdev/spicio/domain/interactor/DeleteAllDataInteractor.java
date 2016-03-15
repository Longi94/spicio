package com.tlongdev.spicio.domain.interactor;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
public interface DeleteAllDataInteractor extends Interactor {
    interface Callback {
        void onFinish();

        void onFail();
    }
}