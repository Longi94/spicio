package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public interface CheckEpisodeInteractor extends Interactor {
    interface Callback {
        void onEpisodeCheckFinish();

        void onEpisodeCheckFail();
    }
}