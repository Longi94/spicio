package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerNetworkComponent;
import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.interactor.impl.TvdbSearchInteractorImpl;
import com.tlongdev.spicio.domain.model.TvdbSeriesOld;
import com.tlongdev.spicio.domain.repository.TvdbRepository;
import com.tlongdev.spicio.module.FakeNetworkRepositoryModule;
import com.tlongdev.spicio.module.SpicioAppModule;
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
 * @since 2016. 02. 28.
 */
@RunWith(MockitoJUnitRunner.class)
public class TvdbSearchInteractorTest {

    @Mock
    private TvdbRepository mRepository;

    @Mock
    private Executor mExecutor;

    @Mock
    private TvdbSearchInteractor.Callback mMockedCallback;

    @Mock
    SpicioApplication mApp;

    private MainThread mMainThread;

    @Before
    public void setUp() {
        mMainThread = new TestMainThread();

        FakeNetworkRepositoryModule networkRepositoryModule = new FakeNetworkRepositoryModule();
        networkRepositoryModule.setTvdbRepository(mRepository);

        NetworkComponent component = DaggerNetworkComponent.builder()
                .spicioAppModule(new SpicioAppModule(mApp))
                .networkRepositoryModule(networkRepositoryModule)
                .build();

        when(mApp.getNetworkComponent()).thenReturn(component);
    }

    @Test
    public void testSearchResult() {

        String searchQuery = "thrones";

        List<TvdbSeriesOld> seriesList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            seriesList.add(mock(TvdbSeriesOld.class));
        }

        when(mRepository.searchSeries(searchQuery)).thenReturn(seriesList);

        TvdbSearchInteractorImpl interactor = new TvdbSearchInteractorImpl(
                mExecutor, mMainThread, mApp, searchQuery, mMockedCallback
        );
        interactor.run();
        verify(mRepository).searchSeries(searchQuery);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onSearchResult(seriesList);
    }

    @Test
    public void testSearchFail() {
        String searchQuery = "thrones";

        when(mRepository.searchSeries(searchQuery)).thenReturn(null);

        TvdbSearchInteractorImpl interactor = new TvdbSearchInteractorImpl(
                mExecutor, mMainThread, mApp, searchQuery, mMockedCallback
        );
        interactor.run();

        verify(mRepository).searchSeries(searchQuery);
        verifyNoMoreInteractions(mRepository);
        verify(mMockedCallback).onSearchFailed();
    }
}
