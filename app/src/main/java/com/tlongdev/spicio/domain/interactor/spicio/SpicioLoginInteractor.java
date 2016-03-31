package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;

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
