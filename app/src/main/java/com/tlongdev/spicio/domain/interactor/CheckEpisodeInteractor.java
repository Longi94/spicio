package com.tlongdev.spicio.domain.interactor;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public interface CheckEpisodeInteractor extends Interactor {
    interface Callback {
        void onFinish();

        void onFail();
    }
}