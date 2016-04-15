package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.UserEpisodes;

/**
 * @author longi
 * @since 2016.04.15.
 */
public interface GetEpisodesInteractor extends Interactor {
    interface Callback {
        void onGetEpisodesFinish(UserEpisodes series);

        void onGetEpisodesFail();
    }
}