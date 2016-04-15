package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author longi
 * @since 2016.04.15.
 */
public interface AddFriendInteractor extends Interactor {
    interface Callback {
        void onAddFriendFinish();

        void onAddFriendFail();
    }
}