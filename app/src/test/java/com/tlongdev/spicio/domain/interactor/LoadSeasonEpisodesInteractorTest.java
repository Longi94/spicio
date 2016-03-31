package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.storage.impl.LoadSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.interactor.storage.LoadSeasonEpisodesInteractor;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.FakeThreadingModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.storage.dao.EpisodeDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadSeasonEpisodesInteractorTest {

    @Mock
    private EpisodeDao mEpisodeDao;

    @Mock
    private LoadSeasonEpisodesInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Mock
    private Series mSeries;

    @Before
    public void setUp() {
        FakeDaoModule storageModule = new FakeDaoModule();
        storageModule.setEpisodeDao(mEpisodeDao);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .daoModule(storageModule)
                .networkRepositoryModule(mock(NetworkRepositoryModule.class))
                .threadingModule(new FakeThreadingModule())
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testSuccess() {

        List<Episode> episodes = new LinkedList<>();
        when(mEpisodeDao.getAllEpisodes(0, 0)).thenReturn(episodes);

        LoadSeasonEpisodesInteractorImpl interactor = new LoadSeasonEpisodesInteractorImpl(
                mApp, 0, 0, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).getAllEpisodes(0, 0);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onLoadSeasonEpisodesFinish(episodes);
    }

    @Test
    public void testFail() {
        when(mEpisodeDao.getAllEpisodes(0, 0)).thenReturn(null);

        LoadSeasonEpisodesInteractorImpl interactor = new LoadSeasonEpisodesInteractorImpl(
                mApp, 0, 0, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).getAllEpisodes(0, 0);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onLoadSeasonEpisodesFail();
    }
}
