package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public interface TraktSearchInteractor extends Interactor {
    interface Callback {
        void onTraktSearchFinish(List<Series> series);

        void onTraktSearchFailed(); // TODO: 2016. 02. 28. needs more descriptive error data
    }
}
