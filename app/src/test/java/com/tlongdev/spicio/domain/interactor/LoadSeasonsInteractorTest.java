package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.LoadSeasonsInteractorImpl;
import com.tlongdev.spicio.domain.model.Season;
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

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadSeasonsInteractorTest {

    @Mock
    private EpisodeDao mEpisodeDao;

    @Mock
    private Executor mExecutor;

    @Mock
    private LoadSeasonsInteractor.Callback mMockedCallback;

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
    public void testLoadSeasons() {

        List<Season> seasons = new LinkedList<>();
        when(mEpisodeDao.getAllSeasons(0)).thenReturn(seasons);

        LoadSeasonsInteractorImpl interactor = new LoadSeasonsInteractorImpl(
                mExecutor, mMainThread, mApp, 0, mMockedCallback
        );
        interactor.run();

        verify(mEpisodeDao).getAllSeasons(0);
        verifyNoMoreInteractions(mEpisodeDao);
        verify(mMockedCallback).onFinish(seasons);
    }
}
