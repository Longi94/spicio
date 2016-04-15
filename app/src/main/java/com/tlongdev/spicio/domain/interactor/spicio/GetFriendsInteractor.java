package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.User;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.15.
 */
public interface GetFriendsInteractor extends Interactor {
    interface Callback {
        void onGetFriendsFinish(List<User> friends);

        void onGetFriendsFail();
    }
}