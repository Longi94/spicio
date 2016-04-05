package com.tlongdev.spicio.domain.interactor.spicio;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public interface SpicioCheckEpisodeInteractor extends Interactor {
    interface Callback {
        void onFinish();

        void onFail();
    }
}