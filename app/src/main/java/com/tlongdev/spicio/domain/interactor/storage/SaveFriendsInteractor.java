package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author lngtr
 * @since 2016. 05. 01.
 */
public interface SaveFriendsInteractor extends Interactor {
    interface Callback {
        void onSaveFriendsFinish();
    }
}