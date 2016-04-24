package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author Long
 * @since 2016. 04. 24.
 */
public interface SaveFeedInteractor extends Interactor {
    interface Callback {
        void onSaveFeedFinish();
    }
}