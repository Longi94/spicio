package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.SaveEpisodesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveEpisodesInteractorTest {

    @Mock
    private EpisodeDao mEpisodeDao;

    @Mock
    private Executor mExecutor;

    @Mock
    private SaveEpisodesInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeDaoModule daoModule = new FakeDaoModule();
        daoModule.setEpisodeDao(mEpisodeDao);

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .daoModule(daoModule)
                .networkRepositoryModule(networkRepositoryModule)
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testInsert(){

        List<Episode> episodes = new LinkedList<>();

        SaveEpisodesInteractorImpl interactor = new SaveEpisodesInteractorImpl(
                mExecutor, mMainThread, mApp, episodes, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).insertAllEpisodes(episodes);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onSaveEpisodesFinish();
    }
}
