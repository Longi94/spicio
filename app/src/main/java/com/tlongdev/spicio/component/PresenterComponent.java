package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.AuthenticationModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.presentation.presenter.activity.LoginPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.SeriesPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.SeriesSearchDetailsPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.SettingsPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.UserPresenter;
import com.tlongdev.spicio.presentation.presenter.fragment.EpisodePresenter;

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

    void inject(SettingsPresenter settingsPresenter);

    void inject(SeriesSearchDetailsPresenter seriesSearchDetailsPresenter);

    void inject(SeriesPresenter seriesPresenter);

    void inject(EpisodePresenter episodePresenter);

    void inject(UserPresenter userPresenter);
}
