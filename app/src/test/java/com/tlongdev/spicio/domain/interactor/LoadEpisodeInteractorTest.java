package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.LoadEpisodeDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.threading.TestMainThread;

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
public class LoadEpisodeInteractorTest {

    @Mock
    private EpisodeDao mEpisodeDao;

    @Mock
    private Executor mExecutor;

    @Mock
    private LoadEpisodeDetailsInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    @Mock
    private Series mSeries;

    private MainThread mMainThread;

    @Before
    public void setUp() {

        mMainThread = new TestMainThread();

        FakeDaoModule storageModule = new FakeDaoModule();
        storageModule.setEpisodeDao(mEpisodeDao);

        InteractorComponent component = DaggerInteractorComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .daoModule(storageModule)
                .networkRepositoryModule(mock(NetworkRepositoryModule.class))
                .build();

        when(mApp.getInteractorComponent()).thenReturn(component);
    }

    @Test
    public void testSuccess() {

        Episode episode = mock(Episode.class);
        when(mEpisodeDao.getEpisode(0, 0, 0)).thenReturn(episode);

        LoadEpisodeDetailsInteractorImpl interactor = new LoadEpisodeDetailsInteractorImpl(
                mExecutor, mMainThread, mApp, 0, 0, 0, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).getEpisode(0, 0, 0);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onFinish(episode);
    }

    @Test
    public void testFail() {
        when(mEpisodeDao.getEpisode(0, 0, 0)).thenReturn(null);

        LoadEpisodeDetailsInteractorImpl interactor = new LoadEpisodeDetailsInteractorImpl(
                mExecutor, mMainThread, mApp, 0, 0, 0, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).getEpisode(0, 0, 0);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onFail();
    }
}
