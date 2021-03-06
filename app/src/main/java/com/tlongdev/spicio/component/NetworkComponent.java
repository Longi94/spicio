package com.tlongdev.spicio.component;

import com.tlongdev.spicio.domain.interactor.impl.TraktSearchInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.impl.TvdbSearchInteractorImpl;
import com.tlongdev.spicio.domain.repository.impl.TraktRepositoryImpl;
import com.tlongdev.spicio.domain.repository.impl.TvdbRepositoryImpl;
import com.tlongdev.spicio.module.NetworkModule;
import com.tlongdev.spicio.module.SpicioAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 02. 25.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, NetworkModule.class})
public interface NetworkComponent {
    void inject(TvdbRepositoryImpl tvdbServiceRepository);
    void inject(TraktRepositoryImpl traktRepository);
}
