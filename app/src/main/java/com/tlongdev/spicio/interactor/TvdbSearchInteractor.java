package com.tlongdev.spicio.interactor;

import com.tlongdev.spicio.model.Series;

import java.util.List;

/**
 * Inner Layer, Interactor.
 * @author Long
 * @since 2016. 02. 28.
 */
public interface TvdbSearchInteractor extends Interactor {

    interface Callback {
        void onSearchResult(List<Series> series);
        void onSearchFailed(); // TODO: 2016. 02. 28. needs more descriptive error data
    }
}
