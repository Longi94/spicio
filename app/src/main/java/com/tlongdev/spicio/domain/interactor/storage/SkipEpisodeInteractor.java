package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author longi
 * @since 2016.04.02.
 */
public interface SkipEpisodeInteractor extends Interactor {
    interface Callback {
        void onEpisodeSkipFinish();

        void onEpisodeSkipFail();
    }
}