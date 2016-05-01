package com.tlongdev.spicio.component;

import com.tlongdev.spicio.module.PresenterModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.presentation.ui.fragment.EpisodeFragment;
import com.tlongdev.spicio.presentation.ui.fragment.FeedFragment;
import com.tlongdev.spicio.presentation.ui.fragment.FriendsFragment;
import com.tlongdev.spicio.presentation.ui.fragment.SearchSeriesFragment;
import com.tlongdev.spicio.presentation.ui.fragment.SeasonsFragment;
import com.tlongdev.spicio.presentation.ui.fragment.SeriesDetailsFragment;
import com.tlongdev.spicio.presentation.ui.fragment.SeriesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author lngtr
 * @since 2016. 05. 01.
 */
@Singleton
@Component(modules = {PresenterModule.class, SpicioAppModule.class})
public interface FragmentComponent {
    void inject(SeriesFragment seriesFragment);

    void inject(SeriesDetailsFragment seriesDetailsFragment);

    void inject(SeasonsFragment seasonsFragment);

    void inject(SearchSeriesFragment searchSeriesFragment);

    void inject(FriendsFragment friendsFragment);

    void inject(FeedFragment feedFragment);

    void inject(EpisodeFragment episodeFragment);
}
