package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.User;

/**
 * @author longi
 * @since 2016.04.17.
 */
public interface GetUserDataInteractor extends Interactor {
    interface Callback {
        void onGetUserDataFinish(User user);

        void onGetUserDataFail();
    }
}