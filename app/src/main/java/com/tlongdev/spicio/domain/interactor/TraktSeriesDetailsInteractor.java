package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.model.Series;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public interface TraktSeriesDetailsInteractor extends Interactor {
    interface Callback {
        void onResult(Series series);
        void onFail(); // TODO: 2016. 02. 28. needs more descriptive error data
    }
}
