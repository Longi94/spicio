package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author Long
 * @since 2016. 03. 31.
 */
public interface AddSeriesInteractor extends Interactor {
    interface Callback {
        void onAddSeriesFinish();

        void onAddSeriesFail();
    }
}