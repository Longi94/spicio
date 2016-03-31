package com.tlongdev.spicio.domain.interactor.trakt;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.Episode;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public interface TraktSeasonEpisodesInteractor extends Interactor {
    interface Callback {
        void onTraktSeasonEpisodesFinish(List<Episode> episodes);

        void onTraktSeasonEpisodesFail();
    }
}