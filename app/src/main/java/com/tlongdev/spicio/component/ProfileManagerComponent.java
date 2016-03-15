package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.util.ProfileManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
@Singleton
@Component(modules = SpicioAppModule.class)
public interface ProfileManagerComponent {
    void inject(ProfileManager profileManager);
}
