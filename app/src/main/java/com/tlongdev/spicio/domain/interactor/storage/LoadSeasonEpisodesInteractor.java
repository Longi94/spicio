package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.Episode;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public interface LoadSeasonEpisodesInteractor extends Interactor {
    interface Callback {
        void onLoadSeasonEpisodesFinish(List<Episode> episodes);

        void onLoadSeasonEpisodesFail();
    }
}