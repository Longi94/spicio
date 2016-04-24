package com.tlongdev.spicio.component;

import com.tlongdev.spicio.domain.interactor.AbstractInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.AddFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.AddSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetFeedInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetFriendsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetFullUserDataInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetHistoryInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetUserDataInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.RemoveFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.RemoveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SearchUsersInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SpicioCheckEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SpicioLikeEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SpicioLoginInteractorImpl;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SpicioSkipEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.CheckEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.DeleteAllDataInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.DeleteFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.IsFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LikeEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadAllSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadEpisodeDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadFeedInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadFriendsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadSeasonsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveActivitiesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveFeedInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveFriendInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.impl.SkipEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.interactor.trakt.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.trakt.impl.TraktSearchInteractorImpl;
import com.tlongdev.spicio.domain.interactor.trakt.impl.TraktSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.trakt.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.interactor.tvdb.impl.TvdbSearchInteractorImpl;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.module.ThreadingModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
@Singleton
@Component(modules = {SpicioAppModule.class, DaoModule.class, NetworkRepositoryModule.class,
        ThreadingModule.class})
public interface InteractorComponent {
    void inject(AbstractInteractor abstractInteractor);

    void inject(SaveSeriesInteractorImpl saveSeriesInteractor);

    void inject(TvdbSearchInteractorImpl tvdbSearchInteractor);

    void inject(TraktSearchInteractorImpl traktSearchInteractor);

    void inject(TraktSeriesDetailsInteractorImpl traktSeriesDetailsInteractor);

    void inject(LoadAllSeriesInteractorImpl loadAllSeriesInteractor);

    void inject(TraktFullSeriesInteractorImpl traktFullSeriesInteractor);

    void inject(LoadSeasonsInteractorImpl loadSeasonsInteractor);

    void inject(LoadSeriesDetailsInteractorImpl loadSeriesDetailsInteractor);

    void inject(TraktSeasonEpisodesInteractorImpl traktEpisodeImagesInteractor);

    void inject(LoadSeasonEpisodesInteractorImpl loadSeasonEpisodesInteractor);

    void inject(SaveEpisodesInteractorImpl saveEpisodesInteractor);

    void inject(LoadEpisodeDetailsInteractorImpl loadEpisodeDetailsInteractor);

    void inject(CheckEpisodeInteractorImpl checkEpisodeInteractor);

    void inject(LikeEpisodeInteractorImpl likeEpisodeInteractor);

    void inject(DeleteAllDataInteractorImpl deleteAllDataInteractor);

    void inject(SpicioLoginInteractorImpl spicioLoginInteractor);

    void inject(GetFullUserDataInteractorImpl getFullUserDataInteractor);

    void inject(SearchUsersInteractorImpl searchUsersInteractor);

    void inject(AddSeriesInteractorImpl addSeriesInteractor);

    void inject(SkipEpisodeInteractorImpl skipEpisodeInteractor);

    void inject(SpicioSkipEpisodeInteractorImpl spicioSkipEpisodeInteractor);

    void inject(SpicioCheckEpisodeInteractorImpl spicioCheckEpisodeInteractor);

    void inject(SpicioLikeEpisodeInteractorImpl spicioLikeEpisodeInteractor);

    void inject(SaveActivitiesInteractorImpl saveActivitiesInteractor);

    void inject(AddFriendInteractorImpl addFriendInteractor);

    void inject(RemoveFriendInteractorImpl removeFriendInteractor);

    void inject(RemoveSeriesInteractorImpl removeSeriesInteractor);

    void inject(GetHistoryInteractorImpl getHistoryInteractor);

    void inject(GetFeedInteractorImpl getFeedInteractor);

    void inject(GetFriendsInteractorImpl getFriendsInteractor);

    void inject(GetSeriesInteractorImpl getSeriesInteractor);

    void inject(GetEpisodesInteractorImpl getEpisodesInteractor);

    void inject(GetUserDataInteractorImpl getUserDataInteractor);

    void inject(LoadFriendsInteractorImpl loadFriendsInteractor);

    void inject(SaveFriendInteractorImpl saveFriendInteractor);

    void inject(DeleteFriendInteractorImpl deleteFriendInteractor);

    void inject(IsFriendInteractorImpl isFriendInteractor);

    void inject(LoadFeedInteractorImpl loadFeedInteractor);

    void inject(SaveFeedInteractorImpl saveFeedInteractor);
}
