package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.AuthenticationModule;
import com.tlongdev.spicio.module.PresenterModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.presentation.ui.activity.LoginActivity;
import com.tlongdev.spicio.presentation.ui.activity.MainActivity;
import com.tlongdev.spicio.presentation.ui.activity.SearchFriendsActivity;
import com.tlongdev.spicio.presentation.ui.activity.SeasonEpisodesActivity;
import com.tlongdev.spicio.presentation.ui.activity.SeriesActivity;
import com.tlongdev.spicio.presentation.ui.activity.SeriesSearchDetailsActivity;
import com.tlongdev.spicio.presentation.ui.activity.SettingsActivity;
import com.tlongdev.spicio.presentation.ui.activity.UserActivity;
import com.tlongdev.spicio.presentation.ui.activity.UserSeriesActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
@Singleton
@Component(modules = {PresenterModule.class, SpicioAppModule.class, AuthenticationModule.class})
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(SearchFriendsActivity searchFriendsActivity);

    void inject(SeasonEpisodesActivity seasonEpisodesActivity);

    void inject(SeriesActivity seriesActivity);

    void inject(SeriesSearchDetailsActivity seriesSearchDetailsActivity);

    void inject(SettingsActivity settingsActivity);

    void inject(UserActivity userActivity);

    void inject(UserSeriesActivity userSeriesActivity);
}
