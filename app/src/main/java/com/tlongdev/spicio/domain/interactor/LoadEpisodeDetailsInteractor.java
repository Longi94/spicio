package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.model.Episode;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public interface LoadEpisodeDetailsInteractor extends Interactor {
    interface Callback {
        void onFinish(Episode episode);

        void onFail();
    }
}