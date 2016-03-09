package com.tlongdev.spicio.component;

import com.tlongdev.spicio.domain.interactor.impl.LoadAllSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSearchInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TvdbSearchInteractorImpl;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.module.SpicioAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, DaoModule.class, NetworkRepositoryModule.class})
public interface InteractorComponent {
    void inject(SaveSeriesInteractorImpl saveSeriesInteractor);

    void inject(TvdbSearchInteractorImpl tvdbSearchInteractor);

    void inject(TraktSearchInteractorImpl traktSearchInteractor);

    void inject(TraktSeriesDetailsInteractorImpl traktSeriesDetailsInteractor);

    void inject(LoadAllSeriesInteractorImpl loadAllSeriesInteractor);

    void inject(TraktFullSeriesInteractorImpl traktFullSeriesInteractor);

    void inject(LoadSeasonsInteractorImpl loadSeasonsInteractor);

    void inject(LoadSeriesDetailsInteractorImpl loadSeriesDetailsInteractor);
}
