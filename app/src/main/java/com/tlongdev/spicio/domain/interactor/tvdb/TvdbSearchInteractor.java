package com.tlongdev.spicio.domain.interactor.tvdb;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;

import java.util.List;

/**
 * Inner Layer, Interactor.
 *
 * @author Long
 * @since 2016. 02. 28.
 */
public interface TvdbSearchInteractor extends Interactor {
    interface Callback {
        void onTvdbSearchFinish(List<TvdbSeriesOld> series);

        void onTvdbSearchFailed(); // TODO: 2016. 02. 28. needs more descriptive error data
    }
}
