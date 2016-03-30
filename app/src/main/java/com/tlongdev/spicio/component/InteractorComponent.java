package com.tlongdev.spicio.component;

import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.impl.CheckEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.DeleteAllDataInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.GetFullUserDataInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LikeEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LoadAllSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LoadEpisodeDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.SaveEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.SearchUsersInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.SpicioLoginInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSearchInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TvdbSearchInteractorImpl;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.module.ThreadingModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, DaoModule.class, NetworkRepositoryModule.class,
        ThreadingModule.class})
public interface InteractorComponent {
    void inject(AbstractInteractor abstractInteractor);

    void inject(SaveSeriesInteractorImpl saveSeriesInteractor);

    void inject(TvdbSearchInteractorImpl tvdbSearchInteractor);

    void inject(TraktSearchInteractorImpl traktSearchInteractor);

    void inject(TraktSeriesDetailsInteractorImpl traktSeriesDetailsInteractor);

    void inject(LoadAllSeriesInteractorImpl loadAllSeriesInteractor);

    void inject(TraktFullSeriesInteractorImpl traktFullSeriesInteractor);

    void inject(LoadSeasonsInteractorImpl loadSeasonsInteractor);

    void inject(LoadSeriesDetailsInteractorImpl loadSeriesDetailsInteractor);

    void inject(TraktSeasonEpisodesInteractorImpl traktEpisodeImagesInteractor);

    void inject(LoadSeasonEpisodesInteractorImpl loadSeasonEpisodesInteractor);

    void inject(SaveEpisodesInteractorImpl saveEpisodesInteractor);

    void inject(LoadEpisodeDetailsInteractorImpl loadEpisodeDetailsInteractor);

    void inject(CheckEpisodeInteractorImpl checkEpisodeInteractor);

    void inject(LikeEpisodeInteractorImpl likeEpisodeInteractor);

    void inject(DeleteAllDataInteractorImpl deleteAllDataInteractor);

    void inject(SpicioLoginInteractorImpl spicioLoginInteractor);

    void inject(GetFullUserDataInteractorImpl getFullUserDataInteractor);

    void inject(SearchUsersInteractorImpl searchUsersInteractor);
}
