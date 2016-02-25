package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.NetworkModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.presenter.SearchSeriesPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 02. 25.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, NetworkModule.class})
public interface NetworkComponent {
    void inject(SearchSeriesPresenter searchSeriesPresenter);
}
