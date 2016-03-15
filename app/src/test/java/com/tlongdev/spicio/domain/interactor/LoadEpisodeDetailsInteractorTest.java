package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.impl.LoadEpisodeDetailsInteractorImpl;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadEpisodeDetailsInteractorTest {

    @Mock
    private EpisodeDao mEpisodeDao;

    @Mock
    private LoadEpisodeDetailsInteractor.Callback mMockedCallback;

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

        Episode episode = mock(Episode.class);
        when(mEpisodeDao.getEpisode(0)).thenReturn(episode);

        LoadEpisodeDetailsInteractorImpl interactor = new LoadEpisodeDetailsInteractorImpl(
                mApp, 0, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).getEpisode(0);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onLoadEpisodeDetailsFinish(episode);
    }

    @Test
    public void testFail() {
        when(mEpisodeDao.getEpisode(0)).thenReturn(null);

        LoadEpisodeDetailsInteractorImpl interactor = new LoadEpisodeDetailsInteractorImpl(
                mApp, 0, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).getEpisode(0);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onLoadEpisodeDetailsFail();
    }
}
