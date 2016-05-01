package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public interface SaveSeriesInteractor extends Interactor {
    interface Callback {
        void onSaveSeriesFinish();
    }
}
