package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerNetworkComponent;
import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.TraktSeriesDetailsInteractorImpl;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.module.FakeAppModule;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
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
 * @since 2016. 03. 04.
 */
@RunWith(MockitoJUnitRunner.class)
public class TraktSeriesDetailsInteractorTest {

    @Mock
    private TraktRepository mRepository;

    @Mock
    private Executor mExecutor;

    @Mock
    private TraktSeriesDetailsInteractor.Callback mMockedCallback;

    @Mock
    private SpicioApplication mApp;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();
        networkRepositoryModule.setTraktRepository(mRepository);

        NetworkComponent component = DaggerNetworkComponent.builder()
                .spicioAppModule(new FakeAppModule(mApp))
                .networkRepositoryModule(networkRepositoryModule)
                .build();

        when(mApp.getNetworkComponent()).thenReturn(component);
    }

    @Test
    public void testSearchResult() {

        Series series = mock(Series.class);
        when(mRepository.getSeriesDetails(0)).thenReturn(series);

        TraktSeriesDetailsInteractorImpl interactor = new TraktSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, mApp, 0, mMockedCallback
        );
        interactor.run();
        verify(mRepository).getSeriesDetails(0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onResult(series);
    }

    @Test
    public void testSearchFail() {

        when(mRepository.getSeriesDetails(0)).thenReturn(null);

        TraktSeriesDetailsInteractorImpl interactor = new TraktSeriesDetailsInteractorImpl(
                mExecutor, mMainThread, mApp, 0, mMockedCallback
        );
        interactor.run();

        verify(mRepository).getSeriesDetails(0);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onFail();
    }
}
