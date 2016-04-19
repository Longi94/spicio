package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.User;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.19.
 */
public interface LoadFriendsInteractor extends Interactor {
    interface Callback {
        void onLoadFriendsFinish(List<User> friends);
    }
}