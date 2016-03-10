package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeasonEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.threading.TestMainThread;

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
public class TraktSeasonEpisodesInteractorTest {

    @Mock
    private TraktRepository mRepository;

    @Mock
    private Executor mExecutor;

    @Mock
    private TraktSeasonEpisodesInteractorImpl.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();
        networkRepositoryModule.setTraktRepository(mRepository);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .networkRepositoryModule(networkRepositoryModule)
                .daoModule(mock(DaoModule.class))
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testSuccess() {
        List<Episode> episodes = new LinkedList<>();

        when(mRepository.getEpisodeImages(0, 0)).thenReturn(episodes);

        TraktSeasonEpisodesInteractorImpl interactor = new TraktSeasonEpisodesInteractorImpl(
                mExecutor, mMainThread, mApp, 0, 0, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getEpisodeImages(0, 0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onFinish(episodes);
    }

    @Test
    public void testFail() {

        when(mRepository.getEpisodeImages(0, 0)).thenReturn(null);

        TraktSeasonEpisodesInteractorImpl interactor = new TraktSeasonEpisodesInteractorImpl(
                mExecutor, mMainThread, mApp, 0, 0, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getEpisodeImages(0, 0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onFail();
    }
}
