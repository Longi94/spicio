package com.tlongdev.spicio.domain.interactor;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public interface LikeEpisodeInteractor extends Interactor {
    interface Callback {
        void onEpisodeLikeFinish();

        void onEpisodeLikeFail();
    }
}