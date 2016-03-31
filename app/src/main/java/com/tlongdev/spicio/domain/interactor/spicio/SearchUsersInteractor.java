package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.User;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public interface SearchUsersInteractor extends Interactor {
    interface Callback {
        void onSearchFinished(List<User> users);
    }
}