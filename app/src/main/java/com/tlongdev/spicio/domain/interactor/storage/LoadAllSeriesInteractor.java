package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
public interface LoadAllSeriesInteractor extends Interactor {
    interface Callback {
        void onLoadAllSeriesFinish(List<Series> series);

        void onLoadAllSeriesFail();
    }
}