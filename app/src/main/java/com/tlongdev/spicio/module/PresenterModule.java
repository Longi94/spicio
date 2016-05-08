package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.presentation.presenter.activity.LoginPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.MainPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.SearchFriendsPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.SeasonEpisodesPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.SeriesPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.SeriesSearchDetailsPresenter;
import com.tlongdev.spicio.presentation.presenter.activity.SettingsPresenter;
import com.tlongdev.spicio.presentation.presenter.fragment.FeedPresenter;
import com.tlongdev.spicio.presentation.presenter.fragment.FriendsPresenter;
import com.tlongdev.spicio.presentation.presenter.fragment.SearchSeriesPresenter;
import com.tlongdev.spicio.presentation.presenter.fragment.SeasonsPresenter;
import com.tlongdev.spicio.presentation.presenter.fragment.SeriesDetailsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author lngtr
 * @since 2016. 05. 01.
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    LoginPresenter provideLoginPresenter(Application application) {
        return new LoginPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    MainPresenter provideMainPresenter(Application application) {
        return new MainPresenter(/*(SpicioApplication) application*/);
    }

    @Provides
    @Singleton
    SearchFriendsPresenter provideSearchFriendsPresenter(Application application) {
        return new SearchFriendsPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    SeasonEpisodesPresenter provideSeasonEpisodesPresenter(Application application) {
        return new SeasonEpisodesPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    SeriesPresenter provideSeriesPresenter(Application application) {
        return new SeriesPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    SeriesSearchDetailsPresenter provideSeriesSearchDetailsPresenter(Application application) {
        return new SeriesSearchDetailsPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    SettingsPresenter provideSettingsPresenter(Application application) {
        return new SettingsPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    FeedPresenter provideFeedPresenter(Application application) {
        return new FeedPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    FriendsPresenter provideFriendsPresenter(Application application) {
        return new FriendsPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    SearchSeriesPresenter provideSearchSeriesPresenter(Application application) {
        return new SearchSeriesPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    SeasonsPresenter provideSeasonsPresenter(Application application) {
        return new SeasonsPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    SeriesDetailsPresenter provideSeriesDetailsPresenter(Application application) {
        return new SeriesDetailsPresenter((SpicioApplication) application);
    }

    @Provides
    @Singleton
    com.tlongdev.spicio.presentation.presenter.fragment.SeriesPresenter provideSeriesPresenter2(Application application) {
        return new com.tlongdev.spicio.presentation.presenter.fragment.SeriesPresenter((SpicioApplication) application);
    }
}
