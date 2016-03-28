package com.tlongdev.spicio.domain.interactor;

/**
 * @author longi
 * @since 2016.03.28.
 */
public interface SpicioLoginInteractor extends Interactor {
    interface Callback {
        void onLoginSuccess(long id);

        void onLoginFailed();
    }
}
