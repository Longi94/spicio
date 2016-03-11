package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.SaveSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeDaoModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.storage.dao.EpisodeDao;
import com.tlongdev.spicio.storage.dao.SeriesDao;
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
 * @since 2016. 03. 05.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveSeriesInteractorTest {

    @Mock
    private SeriesDao mSeriesDao;

    @Mock
    private EpisodeDao mEpisodeDao;

    @Mock
    private Executor mExecutor;

    @Mock
    private SaveSeriesInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeDaoModule daoModule = new FakeDaoModule();
        daoModule.setSeriesDao(mSeriesDao);
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

        Series series = mock(Series.class);
        List<Season> seasons = new LinkedList<>();
        List<Episode> episodes = new LinkedList<>();

        SaveSeriesInteractorImpl interactor = new SaveSeriesInteractorImpl(
                mExecutor, mMainThread, mApp, series, seasons, episodes, mMockedCallback
        );
        interactor.run();

        verify(mSeriesDao).insertSeries(series);
        verifyNoMoreInteractions(mSeriesDao);

        verify(mEpisodeDao).insertAllSeasons(seasons);
        verify(mEpisodeDao).insertAllEpisodes(episodes);
        verifyNoMoreInteractions(mEpisodeDao);

        verify(mMockedCallback).onSaveSeriesFinish();
    }
}
