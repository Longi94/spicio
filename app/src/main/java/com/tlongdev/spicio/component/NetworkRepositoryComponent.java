package com.tlongdev.spicio.component;

import com.tlongdev.spicio.domain.interactor.TvdbSearchInteractorImpl;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.module.SpicioAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, NetworkRepositoryModule.class})
public interface NetworkRepositoryComponent {
    void inject(TvdbSearchInteractorImpl tvdbSearchInteractorInstance);
}
