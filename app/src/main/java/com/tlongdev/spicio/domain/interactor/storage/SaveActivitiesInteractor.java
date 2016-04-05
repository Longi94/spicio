package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public interface SaveActivitiesInteractor extends Interactor {
    interface Callback {
        void onSaveActivitiesFinish();
    }
}