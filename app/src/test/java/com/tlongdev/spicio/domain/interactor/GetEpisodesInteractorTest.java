package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.interactor.spicio.GetEpisodesInteractor;
import com.tlongdev.spicio.domain.interactor.spicio.impl.GetEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.model.UserEpisodes;
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
 * @author longi
 * @since 2016.04.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetEpisodesInteractorTest {

    @Mock
    private SpicioRepository mRepository;

    @Mock
    private GetEpisodesInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

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
    }


    @Test
    public void testSuccess() {
        UserEpisodes episodes = mock(UserEpisodes.class);

        when(mRepository.getEpisodes(1L, 0)).thenReturn(episodes);

        GetEpisodesInteractorImpl interactor = new GetEpisodesInteractorImpl(
                mApp, 1L, 0, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getEpisodes(1L, 0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetEpisodesFinish(episodes);
    }

    @Test
    public void testFail() {
        when(mRepository.getEpisodes(1L, 0)).thenReturn(null);

        GetEpisodesInteractorImpl interactor = new GetEpisodesInteractorImpl(
                mApp, 1L, 0, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getEpisodes(1L, 0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onGetEpisodesFail();
    }
}
