package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.AuthenticationModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.presentation.ui.activity.LoginActivity;
import com.tlongdev.spicio.presentation.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, AuthenticationModule.class})
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);
}
