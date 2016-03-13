package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.AuthenticationModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.presentation.presenter.activity.LoginPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, AuthenticationModule.class})
public interface PresenterComponent {
    void inject(LoginPresenter loginPresenter);
}
