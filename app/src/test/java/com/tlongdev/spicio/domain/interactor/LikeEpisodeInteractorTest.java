package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.LikeEpisodeInteractorImpl;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
@RunWith(MockitoJUnitRunner.class)
public class LikeEpisodeInteractorTest {

    @Mock
    private EpisodeDao mEpisodeDao;

    @Mock
    private Executor mExecutor;

    @Mock
    private LikeEpisodeInteractor.Callback mMockedCallback;

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
    public void testCheck(){

        when(mEpisodeDao.setLiked(0, true)).thenReturn(1);

        LikeEpisodeInteractorImpl interactor = new LikeEpisodeInteractorImpl(
                mExecutor, mMainThread, mApp, 0, true, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).setLiked(0, true);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onEpisodeLikeFinish();
    }

    @Test
    public void testFail(){

        when(mEpisodeDao.setLiked(0, true)).thenReturn(0);

        LikeEpisodeInteractorImpl interactor = new LikeEpisodeInteractorImpl(
                mExecutor, mMainThread, mApp, 0, true, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).setLiked(0, true);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onEpisodeLikeFail();
    }
}
