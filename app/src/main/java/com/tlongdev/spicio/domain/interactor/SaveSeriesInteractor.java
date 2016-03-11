package com.tlongdev.spicio.domain.interactor;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public interface SaveSeriesInteractor extends Interactor {
    interface Callback {
        void onSaveSeriesFinish();

        void onSaveSeriesFail(); // TODO: 2016. 03. 07. better error handing
    }
}
