package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.UserActivity;

import java.util.List;

/**
 * @author Long
 * @since 2016. 04. 24.
 */
public interface LoadFeedInteractor extends Interactor {
    interface Callback {
        void onLoadFeedFinish(List<UserActivity> activities);
    }
}