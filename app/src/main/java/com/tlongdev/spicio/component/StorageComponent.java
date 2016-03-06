package com.tlongdev.spicio.component;

import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.module.StorageModule;
import com.tlongdev.spicio.storage.dao.impl.SeriesDaoImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, StorageModule.class})
public interface StorageComponent {
    void inject(SeriesDaoImpl seriesDao);
    void inject(SaveSeriesInteractorImpl saveSeriesInteractor);
}
