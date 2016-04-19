package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author longi
 * @since 2016.04.19.
 */
public interface DeleteFriendInteractor extends Interactor {
    interface Callback {
        void onDeleteFriendFinish();
    }
}