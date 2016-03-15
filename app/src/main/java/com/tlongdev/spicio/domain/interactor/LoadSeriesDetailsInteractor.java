package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.model.Series;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public interface LoadSeriesDetailsInteractor extends Interactor {
    interface Callback {
        void onLoadSeriesDetailsFinish(Series series);

        void onLoadSeriesDetailsFail();
    }
}