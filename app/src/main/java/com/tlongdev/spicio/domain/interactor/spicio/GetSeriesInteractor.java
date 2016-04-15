package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.15.
 */
public interface GetSeriesInteractor extends Interactor {
    interface Callback {
        void onGetSeriesFinish(List<Series> series);

        void onGetSeriesFail();
    }
}