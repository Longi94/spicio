package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;

/**
 * @author Long
 * @since 2016. 04. 05.
 */
public interface SpicioLikeEpisodeInteractor extends Interactor {
    interface Callback {
        void onSpicioLikeFinish();

        void onSpicioLikeFail();
    }
}