package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.SpicioSkipEpisodeInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.SpicioSkipEpisodeInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.module.FakeThreadingModule;

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
 * @since 2016. 04. 05.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpicioSkipEpisodeInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private SpicioSkipEpisodeInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Mock
    private Episode mEpisode;

    @Before
    public void setUp() {

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();
        networkRepositoryModule.setSpicioRepository(mRepository);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .networkRepositoryModule(networkRepositoryModule)
                .daoModule(mock(DaoModule.class))
                .threadingModule(new FakeThreadingModule())
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);

        when(mEpisode.getSeriesId()).thenReturn(1);
    }

    @Test
    public void testSuccess() {
        when(mRepository.skipEpisode(1L, 1, mEpisode, true)).thenReturn(true);

        SpicioSkipEpisodeInteractorImpl interactor = new SpicioSkipEpisodeInteractorImpl(
                mApp, 1L, mEpisode, true, mMockedCallback
        );
        interactor.run();

        verify(mRepository).skipEpisode(1L, 1, mEpisode, true);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onSpicioSkipFinish();
    }

    @Test
    public void testFail() {
        when(mRepository.skipEpisode(1L, 1, mEpisode, true)).thenReturn(false);

        SpicioSkipEpisodeInteractorImpl interactor = new SpicioSkipEpisodeInteractorImpl(
                mApp, 1L, mEpisode, true, mMockedCallback
        );
        interactor.run();

        verify(mRepository).skipEpisode(1L, 1, mEpisode, true);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onSpicioSkipFail();
    }
}
