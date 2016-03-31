package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public interface SaveEpisodesInteractor extends Interactor {
    interface Callback {
        void onSaveEpisodesFinish();

        void onSaveEpisodesFail();
    }
}