package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.AuthenticationModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.view.NavigationDrawerManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author longi
 * @since 2016.04.29.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, AuthenticationModule.class})
public interface DrawerManagerComponent {
    void inject(NavigationDrawerManager navigationDrawerManager);
}