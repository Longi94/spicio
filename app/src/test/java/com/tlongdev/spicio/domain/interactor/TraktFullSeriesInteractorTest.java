package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.TraktFullSeriesInteractorImpl;
import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Images;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;
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
 * @since 2016. 03. 08.
 */
@RunWith(MockitoJUnitRunner.class)
public class TraktFullSeriesInteractorTest {

    @Mock
    private TraktRepository mRepository;

    @Mock
    private Executor mExecutor;

    @Mock
    private TraktFullSeriesInteractor.Callback mMockedCallback;

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

        Series series = mock(Series.class);
        List<Season> seasons = new LinkedList<>();
        List<Episode> episodes = new LinkedList<>();

        when(mRepository.getImages(0)).thenReturn(new Images());
        when(mRepository.getSeasons(0)).thenReturn(seasons);
        when(mRepository.getEpisodesForSeries(0)).thenReturn(episodes);

        TraktFullSeriesInteractorImpl interactor = new TraktFullSeriesInteractorImpl(
                mExecutor, mMainThread, mApp, series, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getImages(0);
        verify(mRepository).getSeasons(0);
        verify(mRepository).getEpisodesForSeries(0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onTraktFullSeriesFinish(series, seasons, episodes);
    }

    @Test
    public void testFail() {

        Series series = mock(Series.class);

        when(mRepository.getImages(0)).thenReturn(new Images());
        when(mRepository.getSeasons(0)).thenReturn(null);
        when(mRepository.getEpisodesForSeries(0)).thenReturn(null);

        TraktFullSeriesInteractorImpl interactor = new TraktFullSeriesInteractorImpl(
                mExecutor, mMainThread, mApp, series, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getImages(0);
        verify(mRepository).getSeasons(0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onTraktFullSeriesFail();
    }
}
